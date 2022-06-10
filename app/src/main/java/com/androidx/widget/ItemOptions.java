package com.androidx.widget;

import android.graphics.Color;

/**
 * Created by Relin
 * on 2018-07-24.
 */

public class ItemOptions {

    /**
     * 是否是分割的Item
     */
    private boolean isSeparateItem = false;
    /**
     * Item宽度
     */
    private float itemWidth = 0;
    /**
     * Item高度
     */
    private float itemHeight = 0;
    /**
     * Item 横向移动距离
     */
    private float moveX = 0;
    /**
     * Item 竖向移动距离
     */
    private float moveY = 0;
    /**
     * Item 范围最小坐标
     */
    private float minCoordinates[];
    /**
     * Item 范围最大坐标
     */
    private float maxCoordinates[];
    /**
     * Item 分割的左边文字
     */
    private String separateItemLeftText = "LEFT";
    /**
     * Item 分割的右边文字
     */
    private String separateItemRightText = "RIGHT";
    /**
     * Item 上部分文字
     */
    private String itemTopText = "TOP";
    /**
     * Item 下部分文字
     */
    private String itemBottomText = "BOTTOM";
    /**
     * Item 分割的线的颜色（节次/星期）
     */
    private int separateItemLineColor = Color.parseColor("#000000");
    /**
     * Item 分割的左边文字颜色
     */
    private int separateItemLeftTextColor = Color.parseColor("#000000");
    /**
     * Item 分割的右边边文字颜色
     */
    private int separateItemRightTextColor = Color.parseColor("#000000");
    /**
     * Item 上部分文字颜色
     */
    private int itemTopTextColor = Color.parseColor("#000000");
    /**
     * Item 下部分文字颜色
     */
    private int itemBottomTextColor = Color.parseColor("#A2A2A1");
    /**
     * Item 分割部分背景颜色
     */
    private int separateItemBackgroundColor = Color.parseColor("#000000");
    /**
     * Item 背景颜色
     */
    private int itemBackgroundColor = Color.parseColor("#FFFFFF");
    /**
     * Item 按下背景颜色
     */
    private int itemPressedColor = Color.parseColor("#EFEFEF");
    /**
     * Item 弹起背景颜色
     */
    private int itemUnpressedColor = Color.parseColor("#FFFFFF");
    /**
     * Item 分割文字大小
     */
    private float separateItemTextSize = 14;
    /**
     * Item 文字大小
     */
    private float itemTopTextSize = 14;
    /**
     * Item 上部分文字大小
     */
    private float itemBottomTextSize = 13;
    /**
     * Item 上部分文字是否加粗
     */
    private boolean isItemTopBoldText;
    /**
     * Item 下部分文字
     */
    private boolean isItemBottomBoldText;
    /**
     * Item 分割文字是否加粗
     */
    private boolean isSeparateItemBoldText;
    /**
     * Item 是否按下
     */
    private boolean isPressed;


    public boolean isSeparateItem() {
        return isSeparateItem;
    }

    public void setSeparateItem(boolean separateItem) {
        isSeparateItem = separateItem;
    }

    public float getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(float itemWidth) {
        this.itemWidth = itemWidth;
    }

    public float getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(float itemHeight) {
        this.itemHeight = itemHeight;
    }

    public String getSeparateItemLeftText() {
        return separateItemLeftText;
    }

    public void setSeparateItemLeftText(String separateItemLeftText) {
        this.separateItemLeftText = separateItemLeftText;
    }

    public String getSeparateItemRightText() {
        return separateItemRightText;
    }

    public void setSeparateItemRightText(String separateItemRightText) {
        this.separateItemRightText = separateItemRightText;
    }

    public String getItemTopText() {
        return itemTopText;
    }

    public void setItemTopText(String itemTopText) {
        this.itemTopText = itemTopText;
    }

    public String getItemBottomText() {
        return itemBottomText;
    }

    public void setItemBottomText(String itemBottomText) {
        this.itemBottomText = itemBottomText;
    }

    public int getSeparateItemLeftTextColor() {
        return separateItemLeftTextColor;
    }

    public void setSeparateItemLeftTextColor(int separateItemLeftTextColor) {
        this.separateItemLeftTextColor = separateItemLeftTextColor;
    }

    public int getSeparateItemRightTextColor() {
        return separateItemRightTextColor;
    }

    public void setSeparateItemRightTextColor(int separateItemRightTextColor) {
        this.separateItemRightTextColor = separateItemRightTextColor;
    }

    public int getItemTopTextColor() {
        return itemTopTextColor;
    }

    public void setItemTopTextColor(int itemTopTextColor) {
        this.itemTopTextColor = itemTopTextColor;
    }

    public int getItemBottomTextColor() {
        return itemBottomTextColor;
    }

    public void setItemBottomTextColor(int itemBottomTextColor) {
        this.itemBottomTextColor = itemBottomTextColor;
    }

    public int getSeparateItemBackgroundColor() {
        return separateItemBackgroundColor;
    }

    public void setSeparateItemBackgroundColor(int separateItemBackgroundColor) {
        this.separateItemBackgroundColor = separateItemBackgroundColor;
    }

    public int getItemBackgroundColor() {
        return itemBackgroundColor;
    }

    public void setItemBackgroundColor(int itemBackgroundColor) {
        this.itemBackgroundColor = itemBackgroundColor;
    }

    public float getMoveX() {
        return moveX;
    }

    public void setMoveX(float moveX) {
        this.moveX = moveX;
    }

    public float getMoveY() {
        return moveY;
    }

    public void setMoveY(float moveY) {
        this.moveY = moveY;
    }

    public int getSeparateItemLineColor() {
        return separateItemLineColor;
    }

    public void setSeparateItemLineColor(int separateItemLineColor) {
        this.separateItemLineColor = separateItemLineColor;
    }

    public float getSeparateItemTextSize() {
        return separateItemTextSize;
    }

    public void setSeparateItemTextSize(float separateItemTextSize) {
        this.separateItemTextSize = separateItemTextSize;
    }

    public float getItemTopTextSize() {
        return itemTopTextSize;
    }

    public void setItemTopTextSize(float itemTopTextSize) {
        this.itemTopTextSize = itemTopTextSize;
    }

    public float getItemBottomTextSize() {
        return itemBottomTextSize;
    }

    public void setItemBottomTextSize(float itemBottomTextSize) {
        this.itemBottomTextSize = itemBottomTextSize;
    }

    public boolean isItemTopBoldText() {
        return isItemTopBoldText;
    }

    public void setItemTopBoldText(boolean itemTopBoldText) {
        isItemTopBoldText = itemTopBoldText;
    }

    public float[] getMinCoordinates() {
        return minCoordinates;
    }

    public void setMinCoordinates(float x, float y) {
        float[] minCoordinates = new float[]{x, y};
        this.minCoordinates = minCoordinates;
    }

    public float[] getMaxCoordinates() {
        return maxCoordinates;
    }

    public void setMaxCoordinates(float x, float y) {
        float[] maxCoordinates = new float[]{x, y};
        this.maxCoordinates = maxCoordinates;
    }

    public int getItemPressedColor() {
        return itemPressedColor;
    }

    public void setItemPressedColor(int itemPressedColor) {
        this.itemPressedColor = itemPressedColor;
    }

    public boolean isItemBottomBoldText() {
        return isItemBottomBoldText;
    }

    public void setItemBottomBoldText(boolean itemBottomBoldText) {
        isItemBottomBoldText = itemBottomBoldText;
    }

    public boolean isSeparateItemBoldText() {
        return isSeparateItemBoldText;
    }

    public void setSeparateItemBoldText(boolean separateItemBoldText) {
        isSeparateItemBoldText = separateItemBoldText;
    }

    public int getItemUnpressedColor() {
        return itemUnpressedColor;
    }

    public void setItemUnpressedColor(int itemUnpressedColor) {
        this.itemUnpressedColor = itemUnpressedColor;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }
}

