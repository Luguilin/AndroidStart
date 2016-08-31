package lgl.androidstart.tool;

import java.security.MessageDigest;

/**
 * 加密编码
 */
public class EncryptUtils {

	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toMD5(String plainText) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //            System.out.println(buf.toString());// 32位的加密
            //            System.out.println(buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
            return buf.toString().substring(8, 24);
        } catch (Exception e) {
            return "" + (int) (Math.random() * 1000000);//如果加密失败则给出一个随机数
        }
    }

	public static String ABase64(String token) {
		// 看起来用法与Java所带的sun.misc的Base64Encoder这些用法差不多，但应该留意到了，在encode的时候，会有一个参数Flags（即上面代码中的Base64.DEFAULT）
		// 这个参数有什么用呢？根据Android SDK的描述，这种参数有5个：
		// CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
		// DEFAULT 这个参数是默认，使用默认的方法来加密
		// NO_PADDING 这个参数是略去加密字符串最后的”=”
		// NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）
		// URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
		String base64Token = android.util.Base64.encodeToString(token.trim().getBytes(),
				android.util.Base64.NO_WRAP);
		return base64Token;
	}

	public static String getString(byte[] token) {
		byte[] base64Token=android.util.Base64.encode(token, android.util.Base64.DEFAULT);
		return new String(base64Token);
	}
}
