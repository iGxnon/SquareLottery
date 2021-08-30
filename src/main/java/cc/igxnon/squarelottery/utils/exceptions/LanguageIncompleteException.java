package cc.igxnon.squarelottery.utils.exceptions;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
public class LanguageIncompleteException extends Exception {

    public LanguageIncompleteException(String message) {
        super("language " + message + " is incomplete, please delete this language file");
    }

}
