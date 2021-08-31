package cc.igxnon.squarelottery.languages;

import cc.igxnon.squarelottery.SquareLottery;
import cc.igxnon.squarelottery.utils.exceptions.LanguageIncompleteException;
import cc.igxnon.squarelottery.utils.exceptions.LanguageNotFoundException;
import cn.nukkit.utils.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
@SuppressWarnings("unused")
public class Languages {

    public static final String[] availableLanguages = new String[]{"chs", "eng", "cht"};

    public static final LanguageKeys LANGUAGE_KEYS = new LanguageKeys();

    public static Config languageConfig;

    public static void init() {
        SquareLottery.getInstance().saveResource("languages/chs.yml");
        SquareLottery.getInstance().saveResource("languages/cht.yml");
        SquareLottery.getInstance().saveResource("languages/eng.yml");
        String language = SquareLottery.getInstance().getConfig().getString("language");
        try {
            init(language);
        } catch (LanguageNotFoundException | LanguageIncompleteException e) {
            e.printStackTrace();
            return;
        }
        SquareLottery.getInstance().getLogger().info("languages init...");
    }

    public static void init(String language) throws LanguageNotFoundException, LanguageIncompleteException {
        boolean otherLang = SquareLottery.getInstance().getConfig().getBoolean("otherLang");
        if (!otherLang && !Arrays.toString(availableLanguages).contains(language)) {
            throw new LanguageNotFoundException(language);
        }
        init(language, new Config(SquareLottery.getInstance().getDataFolder() + "/languages/" + language + ".yml", Config.YAML));
    }

    public static void init(String language, Config config) throws LanguageIncompleteException {
        if(!LANGUAGE_KEYS.equals(config.getKeys().toArray(new String[0]))) {
            throw new LanguageIncompleteException(language);
        }
        languageConfig = config;
    }

    public static String translate(String languageKey) {
        return languageConfig.getString(languageKey);
    }

    public static class LanguageKeys extends ArrayList<String> {

        public LanguageKeys() {
            add("%config_load_failed%");
            add("%skin_load_failed%");
            add("%lottery_status_free%");
            add("%lottery_status_busy%");
            add("%lottery_status_busy_message%");
            add("%lottery_status_join%");
            add("%lottery_roundabout_prizepool_init%");
            add("%lottery_roundabout_prizepool_config_warning%");
            add("%lottery_roundabout_prizepool_onprize%");
            add("%lottery_roundabout_prizepool_onprize_item_cannotadd%");
            add("%lottery_roundabout_form_menu_title%");
            add("%lottery_roundabout_form_prizepool_content%");
            add("%lottery_roundabout_form_prizepool_start_button%");
            add("%lottery_roundabout_form_prize_back%");
            add("%lottery_roundabout_form_prize_lottery_immediately%");
            add("%lottery_roundabout_form_prize_content%");
            add("%lottery_roundabout_form_prize_prizearranged_true%");
            add("%lottery_roundabout_form_prize_prizearranged_false%");
            add("%lottery_roundabout_prize_count_0%");
        }

        public boolean equals(String[] keys) {
            if(size() != keys.length) {
                return false;
            }
            AtomicBoolean result = new AtomicBoolean(true);
            Stream.of(keys).forEach(key -> {
                if(!contains(key)) {
                    result.set(false);
                }
            });
            return result.get();
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof String[]) {
                return equals((String[]) o);
            }
            return super.equals(o);
        }
    }

}
