package com.hs.tools.common.utils;

/**
 * Created by liyawei on 16-1-13.
 */
public class PinYinUtil {
//    /**
//     * 将字符串中的中文转化为拼音,其他字符不变
//     *
//     * @param inputString
//     * @return
//     */
//    public static String getPingYin(String inputString) {
////        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
////        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
////        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
////        format.setVCharType(HanyuPinyinVCharType.WITH_V);
////
////        char[] input = inputString.trim().toCharArray();
////        String output = "";
////
////        try {
////            for (int i = 0; i < input.length; i++) {
////                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
////                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
////                    output += temp[0];
////                } else
////                    output += Character.toString(input[i]);
////            }
////        } catch (BadHanyuPinyinOutputFormatCombination e) {
////            e.printStackTrace();
////        }
////        if(output.length() > 0) {
////            boolean b = ('a' <= output.charAt(0) && output.charAt(0) <= 'z') || ('A' <= output.charAt(0) && output.charAt(0) <= 'Z');
////            if(b) {
////                return output.toString().toLowerCase();
////            } else {
////                return "#"+output.toString().toLowerCase();
////            }
////        } else {
////            return "#";
////        }
//    }
//
//
//    public static String getPingYinDivider(String inputString) {
////        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
////        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
////        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
////        format.setVCharType(HanyuPinyinVCharType.WITH_V);
////
////        char[] input = inputString.trim().toCharArray();
////        String output = "";
////
////        try {
////            if(isZiMu(inputString)) {
////                output = inputString.trim();
////            } else {
////                for (int i = 0; i < input.length; i++) {
////                    if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
////                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
////                        output += temp[0]+"|";
////                    } else
////                        output += Character.toString(input[i])+"|";
////                }
////            }
////
////        } catch (BadHanyuPinyinOutputFormatCombination e) {
////            e.printStackTrace();
////        }
////        if(output.length() > 0) {
////            boolean b = ('a' <= output.charAt(0) && output.charAt(0) <= 'z') || ('A' <= output.charAt(0) && output.charAt(0) <= 'Z');
////            if(b) {
////                return output.toString().toLowerCase();
////            } else {
////                return "#"+output.toString().toLowerCase();
////            }
////        } else {
////            return "#";
////        }
//    }
//
//
//    public static String getPingYinMans(String inputString) {
////        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
////        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
////        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
////        format.setVCharType(HanyuPinyinVCharType.WITH_V);
////
////        char[] input = inputString.trim().toCharArray();
////        String output = "";
////
////        try {
////            for (int i = 0; i < input.length; i++) {
////                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
////                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
////                    output += temp[0];
////                } else
////                    output += Character.toString(input[i]);
////            }
////        } catch (BadHanyuPinyinOutputFormatCombination e) {
////            e.printStackTrace();
////        }
////        return output;
//    }
//    /**
//     * 获取汉字串拼音首字母，英文字符不变
//     * @param chinese 汉字串
//     * @return 汉语拼音首字母
//     */
//    public static String getFirstSpell(String chinese) {
////        StringBuffer pybf = new StringBuffer();
////        char[] arr = chinese.toCharArray();
////        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
////        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
////        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
////        for (int i = 0; i < arr.length; i++) {
////            if (arr[i] > 128) {
////                try {
////                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
////                    if (temp != null) {
////                        pybf.append(temp[0].charAt(0));
////                    }
////                } catch (BadHanyuPinyinOutputFormatCombination e) {
////                    e.printStackTrace();
////                }
////            } else {
////                pybf.append(arr[i]);
////            }
////        }
////        return pybf.toString().replaceAll("\\W", "").trim();\
//    }
//    /**
//     * 获取汉字串拼音，英文字符不变
//     * @param chinese 汉字串
//     * @return 汉语拼音
//     */
//    public static String getFullSpell(String chinese) {
////        StringBuffer pybf = new StringBuffer();
////        char[] arr = chinese.toCharArray();
////        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
////        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
////        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
////        for (int i = 0; i < arr.length; i++) {
////            if (arr[i] > 128) {
////                try {
////                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
////                } catch (BadHanyuPinyinOutputFormatCombination e) {
////                    e.printStackTrace();
////                }
////            } else {
////                pybf.append(arr[i]);
////            }
////        }
////        return pybf.toString();
//    }
//
////    public static ArrayList<AddressBookBean> getNewStore(ArrayList<AddressBookBean> items) {
////        ArrayList<AddressBookBean> temp = new ArrayList<>();
////
////        ArrayList<AddressBookBean> tempChina = new ArrayList<>();
////        ArrayList<AddressBookBean> tempNum = new ArrayList<>();
////        ArrayList<AddressBookBean> tempAbc = new ArrayList<>();
////        ArrayList<AddressBookBean> tempoth = new ArrayList<>();
////        for(AddressBookBean bean : items) {
////            String name = "";
////            if(!EmptyUtils.isEmpty(bean.name)) {
////                name = bean.name;
////            } else if(!EmptyUtils.isEmpty(bean.contact_name)) {
////                name = bean.contact_name;
////            } else {
////                name = bean.phone;
////            }
////            switch (Common.startWithChina(name)){
////                case 0://其他字符
////                    tempoth.add(bean);
////                    break;
////                case 1://中文字符
////                    tempChina.add(bean);
////                    break;
////                case 2://英文字府
////                    tempAbc.add(bean);
////                    break;
////                case 3://数字
////                    tempNum.add(bean);
////                    break;
////            }
////        }
////        temp.addAll(tempChina);
////        temp.addAll(tempAbc);
////        temp.addAll(tempNum);
////        temp.addAll(tempoth);
////
////        return temp;
////    }
//
//    public static boolean isZiMu(String email) {
//        String telRegex = "[a-zA-Z]+";
//        Pattern p = Pattern.compile(telRegex);
//        Matcher m = p.matcher(email);
//        return m.matches();
//    }
}
