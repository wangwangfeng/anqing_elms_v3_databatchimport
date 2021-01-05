package com.zfsoft.batchimport.utils;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class SM2Util {
    private static ECDomainParameters domainParams;
    
    /**
     * 字符编码
     */
    private static final String DEFAULT_ENCODING = "UTF-8";
    
    /**
     * 获取ECDomainParameters
     * @author luzw
     * @date 2019年9月16日
     * @return
     */
    public static ECDomainParameters getDomainParams() {
    	if (domainParams == null) {
    		BigInteger ecc_p = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF", 16);
        	BigInteger ecc_a = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC", 16);
        	BigInteger ecc_b = new BigInteger("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93", 16);
        	BigInteger ecc_n = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123", 16);
        	BigInteger ecc_gx = new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
        	BigInteger ecc_gy = new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
        	ECCurve curve = new ECCurve.Fp(ecc_p, ecc_a, ecc_b, null, null);
        	ECPoint curve_g = curve.createPoint(ecc_gx, ecc_gy);
        	domainParams = new ECDomainParameters(curve, curve_g, ecc_n, BigInteger.ONE);
    	}
    	return domainParams;
    }

    /**
     * 生成密钥对
     * @author luzw
     * @date 2019年9月16日
     * @return
     */
    public static Map<String, String> generateSM2Keys() throws Exception{
        Map<String, String> keysMap = new HashMap<String, String>();
    	ECKeyGenerationParameters ekgParams = new ECKeyGenerationParameters(getDomainParams(), new SecureRandom());
    	ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
    	keyGen.init(ekgParams);
    	AsymmetricCipherKeyPair keyPair = keyGen.generateKeyPair();
    	//获取公钥
    	ECPublicKeyParameters pubKey = (ECPublicKeyParameters)keyPair.getPublic();
        String pubKeyStr = ByteUtils.toHexString(pubKey.getQ().getEncoded(false));
        keysMap.put("publicKey", pubKeyStr);
        //获取私钥
    	ECPrivateKeyParameters priKey = (ECPrivateKeyParameters)keyPair.getPrivate();
        String priKeyStr = ByteUtils.toHexString(priKey.getD().toByteArray());
        keysMap.put("privateKey", priKeyStr);
    	return keysMap;
    }

    /**
     * SM2公钥加密
     * @author luzw
     * @date 2019年9月16日
     * @param publicKey 公钥字符串
     * @param text 需加密的内容
     * @return
     * @throws Exception
     */
    public static String sm2Encrypt(String publicKey, String text) throws Exception {
        //1、根据公钥字符串获取公钥
        ECDomainParameters domainParams = SM2Util.getDomainParams();
        ECPoint point = domainParams.getCurve().decodePoint(ByteUtils.fromHexString(publicKey));
        ECPublicKeyParameters ecpuKey = new ECPublicKeyParameters(point, domainParams);
        //2、将需要加密的内容转为字节数组
        byte[] data = text.getBytes(DEFAULT_ENCODING);
        //3、进行加密操作
        SM2Engine engine = new SM2Engine();
        ParametersWithRandom pwr = new ParametersWithRandom(ecpuKey, new SecureRandom());
        engine.init(true, pwr);
        return ByteUtils.toHexString(engine.processBlock(data, 0, data.length));
    }
    
    /**
     * SM2私钥解密
     * @author luzw
     * @date 2019年9月16日
     * @param privateKey 私钥字符串
     * @param cipherText 需要解密的内容
     * @return
     * @throws Exception
     */
    public static String sm2Decrypt(String privateKey, String cipherText) throws Exception {
        //1、根据私钥字符串获取私钥
        BigInteger biKey = new BigInteger(privateKey, 16);
        ECPrivateKeyParameters ecprKey = new ECPrivateKeyParameters(biKey, SM2Util.getDomainParams());
        //2、将需要解密的内容转为字节数组
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        //3、进行解密操作
        SM2Engine engine = new SM2Engine();
        engine.init(false, ecprKey);
        return new String(engine.processBlock(cipherData, 0, cipherData.length), DEFAULT_ENCODING);
    }

    /**
     * SM2私钥签名
     * @author luzw
     * @date 2019年9月16日
     * @param privateKey 私钥字符串
     * @param withId 关联键
     * @param text 需要签名的内容
     * @return
     * @throws Exception
     */
    public static String sm2Sign(String privateKey, String withId, String text) throws Exception {
        //1、根据私钥字符串获取私钥
        BigInteger biKey = new BigInteger(privateKey, 16);
        ECPrivateKeyParameters ecprKey = new ECPrivateKeyParameters(biKey, SM2Util.getDomainParams());
        //2、将需要签名的内容转为字节数组
        byte[] data = text.getBytes(DEFAULT_ENCODING);
        //3、进行签名操作
        SM2Signer signer = new SM2Signer();
        CipherParameters param = null;
        ParametersWithRandom pwr = new ParametersWithRandom(ecprKey, new SecureRandom());
        if (withId != null) {
            param = new ParametersWithID(pwr, withId.getBytes(DEFAULT_ENCODING));
        } else {
            param = pwr;
        }
        signer.init(true, param);
        signer.update(data, 0, data.length);
        return ByteUtils.toHexString(signer.generateSignature());
    }

    /**
     * SM2公钥验签
     * @author luzw
     * @date 2019年9月16日
     * @param publicKey 公钥字符串
     * @param withId 关联键
     * @param text 需要签名的内容
     * @param sign 签名字符串
     * @return
     * @throws Exception
     */
    public static boolean sm2Verify(String publicKey, String withId, String text, String sign) throws Exception{
        //1、根据公钥字符串获取公钥
        ECDomainParameters domainParams = SM2Util.getDomainParams();
        ECPoint point = domainParams.getCurve().decodePoint(ByteUtils.fromHexString(publicKey));
        ECPublicKeyParameters ecpuKey = new ECPublicKeyParameters(point, domainParams);
        //2、将需要验签的内容转为字节数组
        byte[] data = text.getBytes(DEFAULT_ENCODING);
        //3、进行验签操作
        SM2Signer signer = new SM2Signer();
        CipherParameters param = null;
        if (withId != null) {
            param = new ParametersWithID(ecpuKey, withId.getBytes(DEFAULT_ENCODING));
        } else {
            param = ecpuKey;
        }
        signer.init(false, param);
        signer.update(data, 0, data.length);
        return signer.verifySignature(ByteUtils.fromHexString(sign));
    }
    
    public static void main(String[] args) throws Exception{
        System.out.println("----SM2测试开始----");
        
        String text = "加密文本abc";
        String withId = "F0E3068A3B85CCC7A44E83C90EC9D4B5";
        Map<String, String> keysMap = SM2Util.generateSM2Keys();
        String privateKey = keysMap.get("privateKey");
        String publicKey = keysMap.get("publicKey");
        System.out.println("原文：" + text);
        System.out.println("私钥:" + privateKey);
        System.out.println("公钥:" + publicKey);
        
        String eText = sm2Encrypt(publicKey, text);
        String dText = sm2Decrypt(privateKey, eText);
        System.out.println("公钥加密信息:" + eText);
        if (!dText.equals(text)) {
            throw new Exception("SM2 解密不对。。。");
        } else {
            System.out.println("私钥解密信息:" + dText);
        }
        
        String sText = sm2Sign(privateKey, withId, text);
        System.out.println("私钥签名信息:" + sText);
        boolean svFlag = sm2Verify(publicKey, withId, text, sText);
        if (!svFlag) {
            throw new Exception("SM2 签名校验不通过。。。");
        } else {
            System.out.println("公钥验签信息:" + svFlag);
        }
        
      /*  int times = 100;
        long t = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            eText = sm2Encrypt(publicKey, text);
            sm2Decrypt(privateKey, eText);
        }
        System.out.println(times + "次非对称加密与解密平均耗时：" + ((double)(System.currentTimeMillis() - t) / times) + "毫秒");
        
        t = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            sText = sm2Sign(privateKey, withId, text);
            sm2Verify(publicKey, withId, text, sText);
        }
        System.out.println(times + "次签名生成与校验平均耗时：" + ((double)(System.currentTimeMillis() - t) / times) + "毫秒");
        */
        System.out.println("----SM2测试结束----");
    }
}