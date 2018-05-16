package com.mjliu.templet.entity;

import com.mjliu.commonlib.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class CurrentOrderEntity extends BaseEntity {

    public CurrentOrder data;

    public static class CurrentOrder implements Serializable {
        public String order_id;
        public String order_sn;
        public String start_place;
        public String end_place;
        public String status; //6前往装货地,7到达装货地,8装货完成,9到达卸货地,10卸货完成

        public String hasTransPoint;//是否有途经点：1有 0无
        public List<TransPoint> transPointList;//装卸货地点，及途经点信息
        public MakePoint makePoint;
        public int show_index;
        public List<Notice> notice;
    }

    public static class Notice {
        public String type;
        public String msg;
        public String order_id;
        public int index;
    }

    public static class TransPoint implements Serializable {
        public String id;
        public String address;
        public String orderSn;
        public String status;
        public String cityId;
        public String provinceName;
        public String cityName;
        public String districtName;
        public String longitude;
        public String latitude;
    }

    public static class MakePoint implements Serializable {
        public int type;
        public String text;
        public int flag;
        public int model;
        public Graph sampleGraph;

        public static class Graph implements Serializable {
            public String big;
        }
    }
}
