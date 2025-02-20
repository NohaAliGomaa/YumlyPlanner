package com.example.yumlyplanner.model.pojo;

import com.example.yumlyplanner.homefragment.view.BindableItem;

public class Area  implements BindableItem {
        private String areaName;
        private int imageResourceId;

        public Area(String areaName, int imageResourceId) {
            this.areaName = areaName;
            this.imageResourceId = imageResourceId;
        }

        public String getAreaName() {
            return areaName;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }

    @Override
    public String getTitle() {
        return getAreaName();
    }

    @Override
    public String getImageUrl() {
        return String.valueOf(getImageResourceId());
    }
}
