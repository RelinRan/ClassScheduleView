package com.androidx.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 课程表控件
 */
public class ClassScheduleView extends View {

    /**
     * 行数
     */
    private int rowCount = 8;
    /**
     * 列数
     */
    private int columnCount = 9;
    /**
     * 标题
     */
    private String titleName = "课程表";
    /**
     * 日期时间
     */
    private String dateTime = "2018年07月23日 星期一 第23周";
    /**
     * 外边距圆角
     */
    private float borderRadius = dpToPx(10F);
    /**
     * 标题高度
     */
    private float titleHeight = dpToPx(40);
    /**
     * 标题左边距
     */
    private float titleLeftPadding = dpToPx(5F);
    /**
     * 标题右边距
     */
    private float titleRightPadding = dpToPx(5);
    /**
     * 标题字体大小
     */
    private float titleTextSize = dpToPx(18);
    /**
     * 标题分割线颜色
     */
    private int titleDividerColor = Color.parseColor("#E9E9E9");
    /**
     * 标题文字颜色
     */
    private int titleTextColor = Color.parseColor("#A8A3A3");
    /**
     * 标题左右箭头颜色
     */
    private int titleTextLeftRightColor = Color.parseColor("#A8A3A3");
    /**
     * 标题左右箭头大小
     */
    private float titleTextLeftRightArrowSize = dpToPx(16);
    /**
     * 日期时间颜色
     */
    private int dateTimeColor = Color.parseColor("#FF8B10");
    /**
     * 时间日期字体大小
     */
    private float dateTimeTextSize = dpToPx(14);
    /**
     * 分割线颜色
     */
    private int dividerColor = Color.parseColor("#000000");
    /**
     * 返回按钮颜色
     */
    private int backCircleColor = Color.parseColor("#FF8B18");
    /**
     * 返回宽度
     */
    private float backCircleWidth = dpToPx(20);
    /**
     * 边框线颜色
     */
    private int borderLineColor = Color.parseColor("#000000");
    /**
     * Item点击颜色
     */
    private int itemPressedColor = Color.parseColor("#E7E6E6");


    /**
     * 星期行数据
     */
    private String rows[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    /**
     * 星期行标记
     */
    private String rowBottoms[] = {"7-23", "7-24", "7-25", "7-26", "7-26", "7-27", "7-28"};
    /**
     * 节列数据
     */
    private String columns[] = {"第一节", "第二节", "第三节", "第四节", "第五节", "第六节", "第七节", "第八节"};
    /**
     * 节列标记
     */
    private String columnBottoms[] = {"08:00~08:40", "08:50~09:30", "09:40~10:20", "10:30~11:10", "11:20~12:00", "14:00~14:40", "14:50~15:30", "15:40~16:20"};
    /**
     * 表距离顶部（含标题）
     */
    private float top;
    /**
     * 控件宽度
     */
    private float viewWidth;
    /**
     * 控件高度
     */
    private float viewHeight;
    /**
     * 标题画笔
     */
    private Paint titlePaint;
    /**
     * 标题日期画笔
     */
    private Paint titleDatePaint;
    /**
     * Item宽度
     */
    private float itemWidth;
    /**
     * Item高度
     */
    private float itemHeight;
    /**
     * 返回坐标-1
     */
    private float backMinCoordinates[];
    /**
     * 返回坐标-2
     */
    private float backMaxCoordinates[];
    /**
     * 缓存Item参数
     */
    private ConcurrentHashMap<String, ItemOptions> itemOptionsMap;
    /**
     * Item参数
     */
    private ItemOptions pressItemOptions;


    public ClassScheduleView(Context context) {
        super(context);
        initAttributeSet(context, null, 0);
    }

    public ClassScheduleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs, 0);
    }

    public ClassScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs, defStyleAttr);
    }

    private void initAttributeSet(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClassScheduleView);
            rowCount = array.getInteger(R.styleable.ClassScheduleView_rowCount, rowCount);
            columnCount = array.getInteger(R.styleable.ClassScheduleView_rowCount, columnCount);
            titleName = array.getString(R.styleable.ClassScheduleView_titleName);
            titleName = TextUtils.isEmpty(titleName) ? "课程表" : titleName;
            dateTime = array.getString(R.styleable.ClassScheduleView_dateTime);
            borderRadius = array.getDimension(R.styleable.ClassScheduleView_borderRadius, borderRadius);
            titleHeight = array.getDimension(R.styleable.ClassScheduleView_titleHeight, titleHeight);
            titleLeftPadding = array.getDimension(R.styleable.ClassScheduleView_titleLeftPadding, titleLeftPadding);
            titleRightPadding = array.getDimension(R.styleable.ClassScheduleView_titleRightPadding, titleRightPadding);
            titleTextSize = array.getDimension(R.styleable.ClassScheduleView_titleTextSize, titleTextSize);
            titleDividerColor = array.getColor(R.styleable.ClassScheduleView_titleDividerColor, titleDividerColor);
            titleTextColor = array.getColor(R.styleable.ClassScheduleView_titleTextColor, titleTextColor);
            titleTextLeftRightColor = array.getColor(R.styleable.ClassScheduleView_titleTextLeftRightColor, titleTextLeftRightColor);
            titleTextLeftRightArrowSize = array.getDimension(R.styleable.ClassScheduleView_titleTextLeftRightArrowSize, titleTextLeftRightArrowSize);
            dateTimeColor = array.getColor(R.styleable.ClassScheduleView_dateTimeColor, dateTimeColor);
            dateTimeTextSize = array.getDimension(R.styleable.ClassScheduleView_dateTimeTextSize, dateTimeTextSize);
            dividerColor = array.getColor(R.styleable.ClassScheduleView_dividerColor, dividerColor);
            backCircleColor = array.getColor(R.styleable.ClassScheduleView_backCircleColor, backCircleColor);
            backCircleWidth = array.getDimension(R.styleable.ClassScheduleView_backCircleWidth, backCircleWidth);
            borderLineColor = array.getColor(R.styleable.ClassScheduleView_borderLineColor, borderLineColor);
            itemPressedColor = array.getColor(R.styleable.ClassScheduleView_itemPressedColor, itemPressedColor);
            array.recycle();
        }
        itemOptionsMap = new ConcurrentHashMap<>();
        top = titleHeight;
        setDefaultDateTime();
        setDefaultWeekMarks();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & event.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                initUnpressedState();
                setPressedItem(event, false);
                return true;
            case MotionEvent.ACTION_DOWN:
                initUnpressedState();
                setPressedItem(event, true);
                return true;
            case MotionEvent.ACTION_UP:
                addBackClickListener(event.getX(), event.getY());
                addItemClickListener(event.getX(), event.getY());
                setPressedItem(event, false);
                return true;
            case MotionEvent.ACTION_MASK:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawView(canvas);
    }

    private void drawView(Canvas canvas) {
        viewWidth = getMeasuredWidth() - getLeft() * 2;
        viewHeight = getMeasuredHeight() - getTop() * 2;
        itemWidth = viewWidth / rowCount;
        itemHeight = (viewHeight - top) / columnCount;
        //返回按钮
        backMinCoordinates = new float[]{getLeft() + 0f, getTop() + 0f};
        backMaxCoordinates = new float[]{getLeft() + titleLeftPadding * 2 + backCircleWidth, getTop() + titleHeight / 2};
        Paint circlePaint = new Paint();
        circlePaint.setStrokeWidth(5F);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(backCircleColor);
        canvas.drawCircle(getLeft() + backCircleWidth / 2 + titleLeftPadding, getTop() + titleHeight / 2, backCircleWidth / 2, circlePaint);
        canvas.drawLine(getLeft() + backCircleWidth / 3 + titleLeftPadding, getTop() + titleHeight / 2, getLeft() + 2 * backCircleWidth / 3 + titleLeftPadding, getTop() + 1 * titleHeight / (2 * 4) + titleHeight / 2, circlePaint);
        canvas.drawLine(getLeft() + backCircleWidth / 3 + titleLeftPadding, getTop() + titleHeight / 2, getLeft() + 2 * backCircleWidth / 3 + titleLeftPadding, getTop() + 3 * titleHeight / (2 * 4), circlePaint);
        //标题
        titlePaint = new Paint();
        titlePaint.setColor(titleTextColor);
        titlePaint.setTextSize(titleTextSize);
        float titleSize = titlePaint.measureText(titleName);
        float singleTitle = titleSize / titleName.length();
        canvas.drawText(titleName, getLeft() + viewWidth / 2 - titleSize / 2, getTop() + titleHeight / 2 + singleTitle / 3, titlePaint);

        Paint titleArrowPaint = new Paint();
        titleArrowPaint.setColor(titleTextLeftRightColor);
        titleArrowPaint.setStrokeWidth(5F);
        titleArrowPaint.setAntiAlias(true);
        titleArrowPaint.setTextSize(titleTextLeftRightArrowSize);
        //左边箭头
        canvas.drawText("<", getLeft() + viewWidth / 2 - titleSize / 2 - dpToPx(10F), getTop() + titleHeight / 2 + singleTitle / 3, titleArrowPaint);
        //右边箭头
        canvas.drawText(">", getLeft() + viewWidth / 2 + titleSize / 2, getTop() + titleHeight / 2 + singleTitle / 3, titleArrowPaint);
        //日期
        titleDatePaint = new Paint();
        titleDatePaint.setTextSize(dateTimeTextSize);
        titleDatePaint.setColor(dateTimeColor);
        float titleDateSize = titleDatePaint.measureText(dateTime);
        canvas.drawText(dateTime, getLeft() * 2 + viewWidth - titleDateSize - titleRightPadding - getLeft(), getTop() + (isLandscape() ? (titleHeight / 2 + singleTitle / 3) : (titleHeight - dpToPx(5))), titleDatePaint);
        //===================================================================Item===========================================================
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                String itemKey = "[" + row + "," + column + "]";
                ItemOptions options = initItem(row, column);
                for (String key : itemOptionsMap.keySet()) {
                    if (key.equals(itemKey)) {
                        options = itemOptionsMap.get(key);
                        initWHCoordinates(options, row, column);
                    }
                }
                itemOptionsMap.put(itemKey, options);
                drawItem(canvas, options);
                //横线
                Paint rowPaint = new Paint();
                rowPaint.setColor(dividerColor);
                rowPaint.setStrokeWidth(2F);
                canvas.drawLine(getLeft() + 0, getTop() + top + column * itemHeight, getLeft() + viewWidth, getTop() + top + column * itemHeight, rowPaint);
            }
            //竖线
            Paint columnPaint = new Paint();
            columnPaint.setColor(dividerColor);
            columnPaint.setStrokeWidth(2F);
            if (row != 0) {
                canvas.drawLine(getLeft() + row * itemWidth, getTop() + top, getLeft() + row * itemWidth, getTop() + viewHeight, columnPaint);
            }

        }
        //课程表分割线
        Paint titlePaint = new Paint();
        titlePaint.setColor(titleDividerColor);
        canvas.drawLine(getLeft() + 0, getTop() + titleHeight, getLeft() + viewWidth, getTop() + titleHeight, titlePaint);
        //边框线
        Paint borderlinesPaint = new Paint();
        borderlinesPaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(5F);
        borderlinesPaint.setStyle(Paint.Style.STROKE);
        borderlinesPaint.setColor(borderLineColor);
        RectF rectF = new RectF(getLeft() + 0, getTop() + 0, getLeft() + viewWidth, getTop() + viewHeight);
        canvas.drawRoundRect(rectF, borderRadius, borderRadius, borderlinesPaint);
    }


    private ItemOptions initItem(int row, int column) {
        String itemKey = "[" + row + "," + column + "]";
        if (itemOptionsMap != null && itemOptionsMap.get(itemKey) != null) {
            itemOptionsMap.get(itemKey).setItemPressedColor(itemPressedColor);
            return itemOptionsMap.get(itemKey);
        }
        ItemOptions options = new ItemOptions();
        initWHCoordinates(options, row, column);
        options.setItemPressedColor(itemPressedColor);
        options.setItemTopTextColor(Color.parseColor("#000000"));
        if (row == 0 && column == 0) {//分割Item
            options.setSeparateItem(true);
            options.setItemTopBoldText(false);
            options.setSeparateItemLeftText("节次");
            options.setSeparateItemRightText("课程");
        } else if (column != 0 && row == 0) {//节次列
            options.setSeparateItem(false);
            options.setItemTopBoldText(true);
            options.setItemBottomTextSize(isLandscape() ? 13 : 10);
            if (column <= columns.length) {
                options.setItemTopText(columns[column - 1]);
                options.setItemBottomText(columnBottoms[column - 1]);
            }
        } else if (row != 0 && column == 0) {//课程列
            options.setSeparateItem(false);
            options.setItemTopBoldText(true);
            options.setItemTopTextColor(Color.parseColor("#FF8B18"));
            if (row <= rows.length) {
                options.setItemTopText(rows[row - 1]);
                options.setItemBottomText(rowBottoms[row - 1]);
            }
        } else if (column != 0 && row != 0) {//课程+老师列
            options.setSeparateItem(false);
            options.setItemTopBoldText(false);
            options.setItemTopText("");
            options.setItemBottomText("");
        }
        return options;
    }

    /**
     * 设置默认日期时间
     */
    private void setDefaultDateTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int total_week = calendar.get(Calendar.WEEK_OF_YEAR);
        dateTime = year + "年" + month + "月" + day + " " + rows[week - 1] + "  第" + total_week + "周";
    }

    /**
     * 设置默认的星期的底部标志文字
     */
    private void setDefaultWeekMarks() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        day = day - week + 1;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        for (int i = 0; i < 7; i++) {
            rowBottoms[i] = decimalFormat.format(month) + "-" + decimalFormat.format((day + i));
        }
    }

    /**
     * 初始化Item宽高和Item坐标
     *
     * @param options
     * @param row
     * @param column
     */
    private void initWHCoordinates(ItemOptions options, int row, int column) {
        options.setItemWidth(itemWidth);
        options.setItemHeight(itemHeight);
        options.setMoveX(getLeft() + itemWidth * row);
        options.setMoveY(getTop() + top + itemHeight * column);
        options.setMinCoordinates(getLeft() + itemWidth * row, getTop() + top + itemHeight * column);
        options.setMaxCoordinates(getLeft() + itemWidth + itemWidth * row, getTop() + itemHeight + top + itemHeight * column);
    }

    /**
     * 画Item
     *
     * @param canvas  画布
     * @param options item参数
     */
    private void drawItem(Canvas canvas, ItemOptions options) {
        Paint paint = new Paint();
        paint.setColor(options.getItemBackgroundColor());
        canvas.drawRect(0 + options.getMoveX(), 0 + options.getMoveY(), options.getItemWidth() + options.getMoveX(), options.getItemHeight() + options.getMoveY(), paint);
        if (options.isSeparateItem()) {//分割Item
            //分割线
            Paint separateCellPaint = new Paint();
            separateCellPaint.setAntiAlias(true);
            separateCellPaint.setColor(options.getSeparateItemLineColor());
            canvas.drawLine(0 + options.getMoveX(), 0 + options.getMoveY(), options.getItemWidth() + options.getMoveX(), options.getItemHeight() + options.getMoveY(), separateCellPaint);
            //文字
            Paint separateCellTextPaint = new Paint();
            separateCellTextPaint.setFakeBoldText(options.isSeparateItemBoldText());
            separateCellTextPaint.setTextSize(dpToPx(options.getSeparateItemTextSize()));
            float textSize = separateCellTextPaint.measureText(options.getSeparateItemLeftText());
            //节次
            separateCellTextPaint.setColor(options.getSeparateItemLeftTextColor());
            canvas.drawText(options.getSeparateItemLeftText(), options.getItemWidth() / 2 - textSize - textSize / 4 + options.getMoveX() + (isLandscape() ? 0 : dpToPx(10F)), options.getItemHeight() / 2 + textSize / 4 + options.getMoveY() + (isLandscape() ? 0 : dpToPx(10F)), separateCellTextPaint);
            //课程
            separateCellTextPaint.setColor(options.getSeparateItemRightTextColor());
            canvas.drawText(options.getSeparateItemRightText(), options.getItemWidth() / 2 + textSize / 4 + options.getMoveX() - (isLandscape() ? 0 : dpToPx(10F)), options.getItemHeight() / 2 + options.getMoveY() - (isLandscape() ? 0 : dpToPx(10F)), separateCellTextPaint);
        } else {//正常Item
            //Item上面的字体
            Paint normalTextPaint = new Paint();
            normalTextPaint.setColor(options.getItemTopTextColor());
            normalTextPaint.setTextSize(dpToPx(options.getItemTopTextSize()));
            normalTextPaint.setFakeBoldText(options.isItemTopBoldText());
            float textSize = normalTextPaint.measureText(options.getItemTopText());
            canvas.drawText(options.getItemTopText(), options.getItemWidth() / 2 - textSize / 2 + options.getMoveX(), options.getItemHeight() / 2 + options.getMoveY(), normalTextPaint);
            //Item下面的字体
            Paint smallTextPaint = new Paint();
            smallTextPaint.setColor(options.getItemBottomTextColor());
            smallTextPaint.setFakeBoldText(options.isItemBottomBoldText());
            smallTextPaint.setTextSize(dpToPx(options.getItemBottomTextSize()));
            float smallTextSize = smallTextPaint.measureText(options.getItemBottomText());
            canvas.drawText(options.getItemBottomText(), options.getItemWidth() / 2 - smallTextSize / 2 + options.getMoveX(), options.getItemHeight() / 2 + dpToPx(14F) + options.getMoveY(), smallTextPaint);
        }
    }

    /**
     * 是否是横屏
     *
     * @return
     */
    private boolean isLandscape() {
        return viewWidth > viewHeight;
    }

    /**
     * 返回键监听接口对象
     */
    public OnBackClickListener onBackClickListener;

    /**
     * 返回键监听事件
     *
     * @param onBackClickListener
     */
    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    /**
     * 返回键监听接口
     */
    public interface OnBackClickListener {
        void onBackClick();
    }

    /**
     * View内部添加返回监听
     *
     * @param upX
     * @param upY
     */
    private void addBackClickListener(float upX, float upY) {
        if (upX > backMinCoordinates[0] && upX < backMaxCoordinates[0] && upY > backMinCoordinates[1] && upY < backMaxCoordinates[1]) {
            if (onBackClickListener != null) {
                onBackClickListener.onBackClick();
            }
        }
    }

    /**
     * Item点击事件接口对象
     */
    public OnItemClickListener onItemClickListener;

    /**
     * Item点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(ItemOptions options, int row, int column, String name, String mark);
    }

    /**
     * 设置Item点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * View内部添加Item点击事件
     *
     * @param x
     * @param y
     */
    private void addItemClickListener(float x, float y) {
        if (onItemClickListener == null) {
            return;
        }
        int item[] = findItemByCoordinates(x, y);
        if (item == null) {
            return;
        }
        String key = "[" + item[0] + "," + item[1] + "]";
        pressItemOptions = itemOptionsMap.get(key);
        if (onItemClickListener != null) {
            if (item[0] == 0 && item[1] == 0) {
                onItemClickListener.onItemClick(pressItemOptions, item[0], item[1], pressItemOptions.getSeparateItemLeftText(), pressItemOptions.getSeparateItemRightText());
            } else {
                onItemClickListener.onItemClick(pressItemOptions, item[0], item[1], pressItemOptions.getItemTopText(), pressItemOptions.getItemBottomText());
            }
        }
    }

    /**
     * 根据坐标找Item位置
     *
     * @param x 横向坐标
     * @param y 纵向坐标
     * @return item位置
     */
    private int[] findItemByCoordinates(float x, float y) {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                pressItemOptions = itemOptionsMap.get("[" + row + "," + column + "]");
                float minCoordinates[] = pressItemOptions.getMinCoordinates();
                float maxCoordinates[] = pressItemOptions.getMaxCoordinates();
                if (minCoordinates[0] <= x && x <= maxCoordinates[0] && minCoordinates[1] <= y && y <= maxCoordinates[1]) {
                    return new int[]{row, column};
                }
            }
        }
        return null;
    }

    /**
     * 初始化Item状态
     */
    private void initUnpressedState() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                ItemOptions options = initItem(row, column);
                options.setItemBackgroundColor(options.getItemUnpressedColor());
                itemOptionsMap.put("[" + row + "," + column + "]", options);
            }
        }
    }

    /**
     * 设置点击Item
     *
     * @param event
     * @param isPressed
     */
    private void setPressedItem(MotionEvent event, boolean isPressed) {
        int item[] = findItemByCoordinates(event.getX(), event.getY());
        if (item == null) {
            return;
        }
        ItemOptions options = initItem(item[0], item[1]);
        options.setItemBackgroundColor(isPressed ? itemPressedColor : options.getItemUnpressedColor());
        itemOptionsMap.put("[" + item[0] + "," + item[1] + "]", options);
        invalidate();
    }

    //============================================================================================

    /**
     * 设置Item背景颜色
     *
     * @param rowIndex    横向item下标，0开始
     * @param columnIndex 竖向item下标，0开始
     * @param color       item背景颜色
     */
    public void setItemBackgroundColor(int rowIndex, int columnIndex, int color) {
        ItemOptions options = initItem(rowIndex, columnIndex);
        options.setItemBackgroundColor(color);
        options.setItemUnpressedColor(color);
        itemOptionsMap.put("[" + rowIndex + "," + columnIndex + "]", options);
        invalidate();
    }

    /**
     * 设置Item文字
     *
     * @param rowIndex    横向item下标，0开始
     * @param columnIndex 竖向item下标，0开始
     * @param top         item上部分文字
     * @param bottom      item下部分文字
     */
    public void setItemText(int rowIndex, int columnIndex, String top, String bottom) {
        ItemOptions options = initItem(rowIndex, columnIndex);
        options.setItemTopText(top);
        options.setItemBottomText(bottom);
        options.setMoveX(options.getMoveX());
        options.setMoveY(options.getMoveY());
        itemOptionsMap.put("[" + rowIndex + "," + columnIndex + "]", options);
        invalidate();
    }

    /**
     * 设置Item文字
     *
     * @param rowIndex     横向item下标，0开始
     * @param columnIndex  竖向item下标，0开始
     * @param top          item上部分文字
     * @param bottom       item下部分文字
     * @param isTopBold    item上部分部分文字是否加粗
     * @param isBottomBold item下部分部分文字是否加粗
     */
    public void setItemText(int rowIndex, int columnIndex, String top, String bottom, boolean isTopBold, boolean isBottomBold) {
        ItemOptions options = initItem(rowIndex, columnIndex);
        options.setItemTopText(top);
        options.setItemBottomText(bottom);
        options.setItemTopBoldText(isTopBold);
        options.setItemBottomBoldText(isBottomBold);
        itemOptionsMap.put("[" + rowIndex + "," + columnIndex + "]", options);
        invalidate();
    }

    /**
     * 设置Item文字颜色
     *
     * @param rowIndex        横向item下标，0开始
     * @param columnIndex     竖向item下标，0开始
     * @param topTextColor    item上部分文字颜色
     * @param bottomTextColor item下部分文字颜色
     */
    public void setItemTextColor(int rowIndex, int columnIndex, int topTextColor, int bottomTextColor) {
        ItemOptions options = initItem(rowIndex, columnIndex);
        options.setItemTopTextColor(topTextColor);
        options.setItemBottomTextColor(bottomTextColor);
        itemOptionsMap.put("[" + rowIndex + "," + columnIndex + "]", options);
        invalidate();
    }

    /**
     * 设置星期几的item背景颜色
     *
     * @param weekIndex 例如：星期一===》1，星期二===》2
     * @param color     item背景颜色
     */
    public void setWeekItemBackgroundColor(int weekIndex, int color) {
        setItemBackgroundColor(weekIndex, 0, color);
    }

    /**
     * 设置星期行全部的item背景颜色
     *
     * @param color
     */
    public void setWeekItemAllBackgroundColor(int color) {
        for (int i = 0; i < rowCount; i++) {
            setItemBackgroundColor(i, 0, color);
        }
    }


    /**
     * 设置星期几的item文字颜色
     *
     * @param weekIndex       例如：星期一===》1，星期二===》2
     * @param topTextColor    item上部分文字颜色
     * @param bottomTextColor item下部分文字颜色
     */
    public void setWeekItemTextColor(int weekIndex, int topTextColor, int bottomTextColor) {
        setItemTextColor(weekIndex, 0, topTextColor, bottomTextColor);
    }

    /**
     * 设置第星期几的多少节课字体颜色
     *
     * @param weekIndex       例如：星期一===》1，星期二===》2
     * @param sectionCount    几节课，例如：3，是星期对应下面的3节课
     * @param topTextColor    item上部分文字颜色
     * @param bottomTextColor item下部分文字颜色
     */
    public void setWeekColumnItemTextColor(int weekIndex, int sectionCount, int topTextColor, int bottomTextColor) {
        for (int i = 0; i < sectionCount; i++) {
            setItemTextColor(weekIndex, i + 1, topTextColor, bottomTextColor);
        }
    }

    /**
     * 设置第星期几的多少节课Item背景颜色
     *
     * @param weekIndex    例如：星期一===》1，星期二===》2
     * @param sectionCount 几节课，例如：3，是星期对应下面的3节课
     * @param color        item背景颜色
     */
    public void setWeekColumnItemBackgroundColor(int weekIndex, int sectionCount, int color) {
        for (int i = 0; i < sectionCount; i++) {
            setItemBackgroundColor(weekIndex, i + 1, color);
        }
    }


    /**
     * 设置“节次”列的item背景颜色
     *
     * @param sectionNum “节次”列下标，例如：第一节 ===  1，第二节 ==== 2
     * @param color      背景颜色，例如：Color.WHITE
     */
    public void setSectionItemBackgroundColor(int sectionNum, int color) {
        setItemBackgroundColor(0, sectionNum, color);
    }

    /**
     * 设置“节次”列的item文字颜色
     *
     * @param sectionNum      “节次”列下标，例如：第一节 ===  1，第二节 ==== 2
     * @param topTextColor    item上部分文字颜色
     * @param bottomTextColor item下部分文字颜色
     */
    public void setSectionItemTextColor(int sectionNum, int topTextColor, int bottomTextColor) {
        ItemOptions options = initItem(0, sectionNum);
        options.setItemTopTextColor(topTextColor);
        options.setItemBottomTextColor(bottomTextColor);
        itemOptionsMap.put("[" + 0 + "," + sectionNum + "]", options);
        invalidate();
    }

    /**
     * 设置被分割的Item（节次/课程）Item背景颜色
     *
     * @param color Item背景颜色
     */
    public void setSeparateItemBackgroundColor(int color) {
        setItemBackgroundColor(0, 1, color);
    }

    /**
     * 设置被分割的Item（节次/课程）Item文字
     *
     * @param left  左边文字
     * @param right 右边文字
     */
    public void setSeparateItemText(String left, String right) {
        ItemOptions options = initItem(0, 0);
        options.setSeparateItemLeftText(left);
        options.setSeparateItemRightText(right);
        itemOptionsMap.put("[" + 0 + "," + 0 + "]", options);
        invalidate();
    }

    /**
     * 设置被分割的Item（节次/课程）Item文字加粗
     *
     * @param isBold 是否加粗
     */
    public void setSeparateItemText(boolean isBold) {
        ItemOptions options = initItem(0, 0);
        options.setSeparateItemBoldText(isBold);
        itemOptionsMap.put("[" + 0 + "," + 0 + "]", options);
        invalidate();
    }

    /**
     * 设置被分割的Item（节次/课程）Item文字颜色
     *
     * @param leftTextColor  item左边分文字颜色
     * @param rightTextColor item右边分文字颜色
     */
    public void setSeparateItemTextColor(int leftTextColor, int rightTextColor) {
        ItemOptions options = initItem(0, 0);
        options.setSeparateItemLeftTextColor(leftTextColor);
        options.setSeparateItemRightTextColor(rightTextColor);
        itemOptionsMap.put("[" + 0 + "," + 0 + "]", options);
        invalidate();
    }

    /**
     * 设置被分割的Item（节次/课程）Item文字大小
     *
     * @param size 文字大小
     */
    public void setSeparateItemTextSize(int size) {
        ItemOptions options = initItem(0, 0);
        options.setSeparateItemTextSize(size);
        itemOptionsMap.put("[" + 0 + "," + 0 + "]", options);
        invalidate();
    }

    /**
     * 通用设置Item文字大小
     *
     * @param rowIndex    横向item下标，0开始
     * @param columnIndex 竖向item下标，0开始
     * @param topSize     item上部分文字大小
     * @param bottomSize  item下部分文字大小
     */
    public void setItemTextSize(int rowIndex, int columnIndex, int topSize, int bottomSize) {
        ItemOptions options = initItem(rowIndex, columnIndex);
        options.setItemTopTextSize(topSize);
        options.setItemBottomTextSize(bottomSize);
        itemOptionsMap.put("[" + rowIndex + "," + columnIndex + "]", options);
        invalidate();
    }

    /**
     * 设置星期几的item文字颜色
     *
     * @param weekIndex      例如：星期一===》1，星期二===》2
     * @param topTextSize    item上部分文字大小
     * @param bottomTextSize item下部分文字大小
     */
    public void setWeekItemTextSize(int weekIndex, int topTextSize, int bottomTextSize) {
        ItemOptions options = initItem(weekIndex, 0);
        options.setItemTopTextSize(topTextSize);
        options.setItemBottomTextSize(bottomTextSize);
        itemOptionsMap.put("[" + weekIndex + "," + 0 + "]", options);
        invalidate();
    }

    /**
     * 设置星期行全部的item文字大小
     *
     * @param topTextSize    item上部分文字大小
     * @param bottomTextSize item下部分文字大小
     */
    public void setWeekItemAllTextSize(int topTextSize, int bottomTextSize) {
        for (int i = 0; i < rowCount; i++) {
            ItemOptions options = initItem(i, 0);
            options.setItemTopTextSize(topTextSize);
            options.setItemBottomTextSize(bottomTextSize);
            itemOptionsMap.put("[" + i + "," + 0 + "]", options);
        }
        invalidate();
    }

    /**
     * 设置“节次”列部分的item文字大小
     *
     * @param sectionNum     “节次”下面的item数量
     * @param topTextSize    item上部分文字大小
     * @param bottomTextSize item下部分文字大小
     */
    public void setSectionItemTextSize(int sectionNum, int topTextSize, int bottomTextSize) {
        for (int i = 0; i < sectionNum; i++) {
            ItemOptions options = initItem(0, (i + 1));
            options.setItemTopTextSize(topTextSize);
            options.setItemBottomTextSize(bottomTextSize);
            itemOptionsMap.put("[" + 0 + "," + (i + 1) + "]", options);
        }
        invalidate();
    }

    /**
     * 设置“节次”列的item文字大小
     *
     * @param topTextSize    item上部分文字大小
     * @param bottomTextSize item下部分文字大小
     */
    public void setSectionItemsAllTextSize(int topTextSize, int bottomTextSize) {
        for (int i = 0; i < columnCount; i++) {
            ItemOptions options = initItem(0, (i + 1));
            options.setItemTopTextSize(topTextSize);
            options.setItemBottomTextSize(bottomTextSize);
            itemOptionsMap.put("[" + 0 + "," + (i + 1) + "]", options);
        }
        invalidate();
    }


    /**
     * 设置按下状态的颜色
     *
     * @param color 颜色值
     */
    public void setItemPressedColor(int color) {
        itemPressedColor = color;
        invalidate();
    }


    /**
     * 设置标题大小
     *
     * @param titleTextSize
     */
    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
        invalidate();
    }

    /**
     * 设置标题颜色
     *
     * @param titleTextColor
     */
    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        invalidate();
    }

    /**
     * 设置标题左右箭头大小
     *
     * @param titleTextLeftRightArrowSize
     */
    public void setTitleLeftAndRightArrowSize(float titleTextLeftRightArrowSize) {
        this.titleTextLeftRightArrowSize = titleTextLeftRightArrowSize;
        invalidate();
    }

    /**
     * 设置标题栏左边
     *
     * @param titleLeftPadding
     */
    public void setTitleLeftPadding(float titleLeftPadding) {
        this.titleLeftPadding = titleLeftPadding;
        invalidate();
    }

    /**
     * 设置标题右边间距
     *
     * @param titleRightPadding
     */
    public void setTitleRightPadding(float titleRightPadding) {
        this.titleRightPadding = titleRightPadding;
        invalidate();
    }

    /**
     * 设置日期时间颜色
     *
     * @param dateTimeColor
     */
    public void setDateTimeColor(int dateTimeColor) {
        this.dateTimeColor = dateTimeColor;
        invalidate();
    }

    /**
     * 设置分割线颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    /**
     * 设置日期时间文字大小
     *
     * @param dateTimeTextSize
     */
    public void setDateTimeTextSize(float dateTimeTextSize) {
        this.dateTimeTextSize = dpToPx(dateTimeTextSize);
        invalidate();
    }

    /**
     * 设置返回图的颜色
     *
     * @param backCircleColor
     */
    public void setBackCircleColor(int backCircleColor) {
        this.backCircleColor = backCircleColor;
        invalidate();
    }

    /**
     * 设置标题分割线颜色
     *
     * @param titleDividerColor
     */
    public void setTitleDividerColor(int titleDividerColor) {
        this.titleDividerColor = titleDividerColor;
        invalidate();
    }

    /**
     * 设置返回按钮线的粗细
     *
     * @param backCircleWidth
     */
    public void setBackCircleWidth(float backCircleWidth) {
        this.backCircleWidth = backCircleWidth;
        invalidate();
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        this.titleName = title;
        invalidate();
    }

    /**
     * 设置标题栏右边文字（日期时间）
     *
     * @param text 文字
     */
    public void setDateTime(String text) {
        this.dateTime = text;
        invalidate();
    }

    /**
     * 设置日期时间
     *
     * @param time 日期
     */
    private void setDateTime(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        int total_week = calendar.get(Calendar.WEEK_OF_YEAR);
        dateTime = year + "年" + month + "月" + day + " " + rows[week - 1] + "  第" + total_week + "周";
        invalidate();
    }

    /**
     * 设置星期列
     *
     * @param rows
     */
    public void setWeekRows(String[] rows) {
        this.rows = rows;
        invalidate();
    }

    /**
     * 设置星期列的日期
     *
     * @param rowBottoms
     */
    public void setWeekRowMarks(String[] rowBottoms) {
        this.rowBottoms = rowBottoms;
        invalidate();
    }

    /**
     * 设置节次列
     *
     * @param columns
     */
    public void setSectionColumns(String[] columns) {
        this.columns = columns;
        invalidate();
    }

    /**
     * 设置节次类的时间
     *
     * @param columnBottoms
     */
    public void setSectionColumnMarks(String[] columnBottoms) {
        this.columnBottoms = columnBottoms;
        invalidate();
    }

    /**
     * 设置标题栏右边文字距离右边的边距
     *
     * @param titleRightPadding
     */
    public void setDateTimePadding(float titleRightPadding) {
        this.titleRightPadding = titleRightPadding;
        invalidate();
    }

    /**
     * 设置行数
     *
     * @param rowCount
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        invalidate();
    }

    /**
     * 设置列数
     *
     * @param columnCount
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        invalidate();
    }

    /**
     * 设置边框圆角
     *
     * @param borderRadius
     */
    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        invalidate();
    }

    /**
     * 设置标题栏高度
     *
     * @param titleHeight
     */
    public void setTitleHeight(float titleHeight) {
        this.titleHeight = titleHeight;
        invalidate();
    }


    /**
     * Get the screen of density
     *
     * @return
     */
    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * The screen of width.
     *
     * @return
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * The screen of height.
     *
     * @return
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * Px to dp
     *
     * @param px
     * @return
     */
    public static float pxToDp(float px) {
        return px / getScreenDensity();
    }

    /**
     * dp to px
     *
     * @param dp
     * @return
     */
    public static float dpToPx(float dp) {
        return dp * getScreenDensity();
    }


    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int spToPx(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int pxToSp(float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
