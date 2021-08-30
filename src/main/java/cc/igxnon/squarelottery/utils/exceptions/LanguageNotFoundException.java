package cc.igxnon.squarelottery.utils.exceptions;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
public class LanguageNotFoundException extends Exception {

    public LanguageNotFoundException(String message) {
        super("language " + message + " not found! please check it existence or use one in ['chs', 'eng', 'cht']");
    }

}
