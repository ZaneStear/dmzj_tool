package com.mirroring.bean;

import java.util.List;

/**
 * json罗列的所有章节，和ChapterInfo比对
 */
public class ChapterJsonBean {

    /**
     * volume_id : 10278
     * id : 10278
     * volume_name : web版本篇
     * volume_order : 1
     * chapters : [{"chapter_id":98742,"chapter_name":"第一章","chapter_order":10},{"chapter_id":98743,"chapter_name":"第二章","chapter_order":20},{"chapter_id":98744,"chapter_name":"第三章","chapter_order":30},{"chapter_id":98745,"chapter_name":"第四章","chapter_order":40},{"chapter_id":98746,"chapter_name":"第五章","chapter_order":50},{"chapter_id":98747,"chapter_name":"第六章","chapter_order":60},{"chapter_id":98748,"chapter_name":"第七章","chapter_order":70},{"chapter_id":98749,"chapter_name":"第八章","chapter_order":80},{"chapter_id":98750,"chapter_name":"第九章","chapter_order":90},{"chapter_id":98751,"chapter_name":"第十章","chapter_order":100},{"chapter_id":98753,"chapter_name":"第十一章","chapter_order":110},{"chapter_id":98754,"chapter_name":"第十二章","chapter_order":120},{"chapter_id":98755,"chapter_name":"第十三章","chapter_order":130},{"chapter_id":98756,"chapter_name":"第十四章","chapter_order":140},{"chapter_id":98757,"chapter_name":"第十五章","chapter_order":150},{"chapter_id":98758,"chapter_name":"第十六章","chapter_order":160},{"chapter_id":98759,"chapter_name":"第十七章","chapter_order":170},{"chapter_id":99515,"chapter_name":"第十八章","chapter_order":180},{"chapter_id":99516,"chapter_name":"第十九章","chapter_order":190},{"chapter_id":99585,"chapter_name":"第二十章","chapter_order":200},{"chapter_id":100443,"chapter_name":"第二十一章","chapter_order":210},{"chapter_id":100708,"chapter_name":"第二十二章","chapter_order":220},{"chapter_id":101197,"chapter_name":"第二十三章","chapter_order":230},{"chapter_id":105302,"chapter_name":"第二十四章","chapter_order":240},{"chapter_id":105785,"chapter_name":"第二十五章","chapter_order":250},{"chapter_id":105786,"chapter_name":"第二十六章","chapter_order":260},{"chapter_id":105787,"chapter_name":"第二十七章","chapter_order":270}]
     */

    private int volume_id;
    private int id;
    private String volume_name;
    private int volume_order;
    private List<ChaptersBean> chapters;

    public int getVolume_id() {
        return volume_id;
    }

    public void setVolume_id(int volume_id) {
        this.volume_id = volume_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<ChaptersBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChaptersBean> chapters) {
        this.chapters = chapters;
    }

    public static class ChaptersBean {
        /**
         * chapter_id : 98742
         * chapter_name : 第一章
         * chapter_order : 10
         */

        private int chapter_id;
        private String chapter_name;
        private int chapter_order;

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getChapter_name() {
            return chapter_name;
        }

        public void setChapter_name(String chapter_name) {
            this.chapter_name = chapter_name;
        }

        public int getChapter_order() {
            return chapter_order;
        }

        public void setChapter_order(int chapter_order) {
            this.chapter_order = chapter_order;
        }
    }
}
