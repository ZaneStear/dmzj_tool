package com.mirroring.bean;

import java.util.List;

/**
 * json罗列的小说信息
 */
public class NovelJsonBean {

    /**
     * id : 2708
     * name : 劣等眼的转生魔术师
     * zone : 日本
     * status : 连载中
     * last_update_volume_name : 第二卷
     * last_update_chapter_name : 插画
     * last_update_volume_id : 10807
     * last_update_chapter_id : 105935
     * last_update_time : 1588037908
     * cover : https://xs.dmzj.com/img/webpic/9/ldy0808l.jpg
     * hot_hits : 243127
     * introduction : 　　最强转生魔术师在异世界开无双！
     　　在由天生眼睛颜色决定能力的世界，曾经有一名能力压倒群雄的天才魔术师。其名为亚伯。因为能力太强，亚伯甚至遭到同伴疏远。为了追求理想世界，他让灵魂转生到遥远的未来。
     　　但是，在未来世界，亚伯拥有的至高眼睛不知为何沦为『劣等眼』，并受人轻视！他被贵族少爷缠上，遭受莫须有的歧视。然而，在由于文明发达导致魔术师的能力显著衰退的未来世界中，亚伯拥有的『琥珀眼』却隐藏著超越人类能理解的非寻常力量！
     　　来自过去的最强英雄，自由自在地打破未来魔术师的常识！
     * types : ["异界/穿越"]
     * authors : 柑橘ゆすら
     * first_letter : L
     * subscribe_num : 10006
     * redis_update_time : 1588212878
     * volume : [{"id":10275,"lnovel_id":2708,"volume_name":"第一卷 受虐的前勇者在未来世界从容生活","volume_order":10,"addtime":1565263758,"sum_chapters":6},{"id":10807,"lnovel_id":2708,"volume_name":"第二卷","volume_order":20,"addtime":1588037006,"sum_chapters":6}]
     */

    private int id;
    private String name;
    private String zone;
    private String status;
    private String last_update_volume_name;
    private String last_update_chapter_name;
    private int last_update_volume_id;
    private int last_update_chapter_id;
    private int last_update_time;
    private String cover;
    private int hot_hits;
    private String introduction;
    private String authors;
    private String first_letter;
    private int subscribe_num;
    private int redis_update_time;
    private List<String> types;
    private List<VolumeBean> volume;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_update_volume_name() {
        return last_update_volume_name;
    }

    public void setLast_update_volume_name(String last_update_volume_name) {
        this.last_update_volume_name = last_update_volume_name;
    }

    public String getLast_update_chapter_name() {
        return last_update_chapter_name;
    }

    public void setLast_update_chapter_name(String last_update_chapter_name) {
        this.last_update_chapter_name = last_update_chapter_name;
    }

    public int getLast_update_volume_id() {
        return last_update_volume_id;
    }

    public void setLast_update_volume_id(int last_update_volume_id) {
        this.last_update_volume_id = last_update_volume_id;
    }

    public int getLast_update_chapter_id() {
        return last_update_chapter_id;
    }

    public void setLast_update_chapter_id(int last_update_chapter_id) {
        this.last_update_chapter_id = last_update_chapter_id;
    }

    public int getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(int last_update_time) {
        this.last_update_time = last_update_time;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getHot_hits() {
        return hot_hits;
    }

    public void setHot_hits(int hot_hits) {
        this.hot_hits = hot_hits;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public int getSubscribe_num() {
        return subscribe_num;
    }

    public void setSubscribe_num(int subscribe_num) {
        this.subscribe_num = subscribe_num;
    }

    public int getRedis_update_time() {
        return redis_update_time;
    }

    public void setRedis_update_time(int redis_update_time) {
        this.redis_update_time = redis_update_time;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<VolumeBean> getVolume() {
        return volume;
    }

    public void setVolume(List<VolumeBean> volume) {
        this.volume = volume;
    }

    public static class VolumeBean {
        /**
         * id : 10275
         * lnovel_id : 2708
         * volume_name : 第一卷 受虐的前勇者在未来世界从容生活
         * volume_order : 10
         * addtime : 1565263758
         * sum_chapters : 6
         */

        private int id;
        private int lnovel_id;
        private String volume_name;
        private int volume_order;
        private int addtime;
        private int sum_chapters;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLnovel_id() {
            return lnovel_id;
        }

        public void setLnovel_id(int lnovel_id) {
            this.lnovel_id = lnovel_id;
        }

        public String getVolume_name() {
            return volume_name;
        }

        public void setVolume_name(String volume_name) {
            this.volume_name = volume_name;
        }

        public int getVolume_order() {
            return volume_order;
        }

        public void setVolume_order(int volume_order) {
            this.volume_order = volume_order;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getSum_chapters() {
            return sum_chapters;
        }

        public void setSum_chapters(int sum_chapters) {
            this.sum_chapters = sum_chapters;
        }
    }
}
