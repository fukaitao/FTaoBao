package com.fuandtan.ftaobao.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.fuandtan.ftaobao.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:the custom widget of slider image
 */

public class SliderLayout extends RelativeLayout implements View.OnTouchListener, ViewSwitcher.ViewFactory {
    private ImageSwitcher switcherImage;//ImageSwitcher
    private int pictureIndex = 0;//index
    private float touchDownX;// touch down x
    private float touchUpX;// touch up x
    private static final int START_AUTO_PLAY = 0;
    private int autoPlayDuration = 4000;
    Drawable unSelectedDrawable;//the unSelected Drawable
    Drawable selectedDrawable;//the Selected Drawable
    private LinearLayout indicatorContainer;//indicator layout
    private Context context;//context
    private List<Object> list = new ArrayList<>();//the list of resourceId or urls
    private int itemCount;//the size ot list
    private Boolean isAutoPlay = true;//the flag of play
    private Dialog loadingDialog;//the loading dialog
    private IndicatorShape indicatorShape = IndicatorShape.oval;//the default shape of indicator
    private IndicatorPosition indicatorPosition = IndicatorPosition.centerBottom;//the default position of indicator
    private int unSelectedIndicatorColor = 0xffffffff;//the default color of the unselected indicator
    private int selectedIndicatorColor = 0xff0000ff;//the default color of the selected indicator
    private float unSelectedIndicatorHeight = getResources().getDimension(R.dimen.sl_unselected_indicator_height);//the default height of the unselected indicator
    private float unSelectedIndicatorWidth = getResources().getDimension(R.dimen.sl_unselected_indicator_width);//the default width of the unselected indicator
    private float selectedIndicatorHeight = getResources().getDimension(R.dimen.sl_selected_indicator_height);//the default height of the selected indicator
    private float selectedIndicatorWidth = getResources().getDimension(R.dimen.sl_selected_indicator_width);//the default width of the selected indicator
    private float indicatorSpace = getResources().getDimension(R.dimen.sl_indicator_padding);//the padding of indicator
    private float indicatorMargin = getResources().getDimension(R.dimen.sl_indicator_margin);//the margin of indicator
    private Integer defaultImage = R.drawable.ic_default;//the default image
    private Integer errorImage = R.drawable.ic_default;//the error image
    private IOnClickListener listener;//listener
    private ArcShape arcShape = ArcShape.inSide;
    private float arcHeight = getResources().getDimension(R.dimen.sl_arc_height);
    private ArcPosition arcPosition = ArcPosition.bottom;
    private int height = 0;
    private int width = 0;
    private Path clipPath;

    //Arc Shape
    private enum ArcShape {
        inSide, outSide
    }

    //Indicator Shape
    private enum IndicatorShape {
        oval, rect
    }

    //Indicator Position
    private enum IndicatorPosition {
        centerBottom,
        rightBottom,
        leftBottom,
        centerTop,
        rightTop,
        leftTop
    }

    //Arc Position
    private enum ArcPosition {
        top,
        bottom,
        left,
        right
    }

    private Target mTarget = new Target() {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            dismissDialog();
            //the code that make the height of ImageSwitcher is wrap_content
//            int height = ((ImageView) switcherImage.getCurrentView()).getMeasuredHeight();
//            ((ImageView) switcherImage.getCurrentView()).setScaleType(ImageView.ScaleType.CENTER_CROP);
//            if (height > 0) {
//                ((ImageView) switcherImage.getCurrentView()).setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT, height));
//            } else {
//                ((ImageView) switcherImage.getCurrentView()).setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT, ImageSwitcher.LayoutParams.MATCH_PARENT));
//            }
            ((ImageView) switcherImage.getCurrentView()).setScaleType(ImageView.ScaleType.CENTER_CROP);
            ((ImageView) switcherImage.getCurrentView()).setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT, ImageSwitcher.LayoutParams.MATCH_PARENT));
            ((ImageView) switcherImage.getCurrentView()).setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            dismissDialog();
            ((ImageView) switcherImage.getCurrentView()).setImageDrawable(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            showDialog();
        }
    };

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case START_AUTO_PLAY:
                    SliderRightToLeft();
                    handler.sendEmptyMessageDelayed(START_AUTO_PLAY, autoPlayDuration);
                    break;
            }
            return false;
        }
    });

    public SliderLayout(Context context) {
        super(context);
        this.context = context;
        initView(context, null, 0);
    }

    public SliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context, attrs, 0);
    }

    public SliderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs, defStyleAttr);
    }

    /**
     * init
     *
     * @param context      context
     * @param attrs        attrs
     * @param defStyleAttr defStyleAttr
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SliderLayout, defStyleAttr, 0);
        if (array != null) {
            isAutoPlay = array.getBoolean(R.styleable.SliderLayout_sl_is_auto_play, isAutoPlay);
            //get the shape of indicator
            int intShape = array.getInt(R.styleable.SliderLayout_sl_indicator_shape, indicatorShape.ordinal());
            for (IndicatorShape shape : IndicatorShape.values()) {
                if (shape.ordinal() == intShape) {
                    indicatorShape = shape;
                    break;
                }
            }
            //get the position of indicator
            int intPosition = array.getInt(R.styleable.SliderLayout_sl_indicator_position, IndicatorPosition.centerBottom.ordinal());
            for (IndicatorPosition position : IndicatorPosition.values()) {
                if (position.ordinal() == intPosition) {
                    indicatorPosition = position;
                    break;
                }
            }

            //get the shape of arc
            int intArcShape = array.getInt(R.styleable.SliderLayout_sl_arc_shape, arcShape.ordinal());
            for (ArcShape shape : ArcShape.values()) {
                if (shape.ordinal() == intArcShape) {
                    arcShape = shape;
                    break;
                }
            }
            //get the position of arc
            int intArcPosition = array.getInt(R.styleable.SliderLayout_sl_arc_position, arcPosition.ordinal());
            for (ArcPosition position : ArcPosition.values()) {
                if (position.ordinal() == intArcPosition) {
                    arcPosition = position;
                    break;
                }
            }

            unSelectedIndicatorColor = array.getColor(R.styleable.SliderLayout_sl_unselected_indicator_color, unSelectedIndicatorColor);
            selectedIndicatorColor = array.getColor(R.styleable.SliderLayout_sl_selected_indicator_color, selectedIndicatorColor);
            unSelectedIndicatorHeight = array.getDimension(R.styleable.SliderLayout_sl_unselected_indicator_height, unSelectedIndicatorHeight);
            unSelectedIndicatorWidth = array.getDimension(R.styleable.SliderLayout_sl_unselected_indicator_width, unSelectedIndicatorWidth);
            selectedIndicatorHeight = array.getDimension(R.styleable.SliderLayout_sl_selected_indicator_height, selectedIndicatorHeight);
            selectedIndicatorWidth = array.getDimension(R.styleable.SliderLayout_sl_selected_indicator_width, selectedIndicatorWidth);
            indicatorSpace = array.getDimension(R.styleable.SliderLayout_sl_indicator_space, indicatorSpace);
            indicatorMargin = array.getDimension(R.styleable.SliderLayout_sl_indicator_margin, indicatorMargin);
            autoPlayDuration = array.getInt(R.styleable.SliderLayout_sl_auto_play_duration, autoPlayDuration);
            defaultImage = array.getResourceId(R.styleable.SliderLayout_sl_default_image, defaultImage);
            errorImage = array.getResourceId(R.styleable.SliderLayout_sl_error_image, errorImage);
            arcHeight = array.getDimension(R.styleable.SliderLayout_sl_arc_height, arcHeight);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }

        //draw the unselected drawable
        LayerDrawable unSelectedLayerDrawable;
        LayerDrawable selectedLayerDrawable;
        GradientDrawable unSelectedGradientDrawable;
        unSelectedGradientDrawable = new GradientDrawable();

        //draw the selected drawable
        GradientDrawable selectedGradientDrawable;
        selectedGradientDrawable = new GradientDrawable();
        switch (indicatorShape) {
            case rect:
                unSelectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                selectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                break;
            case oval:
                unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
                selectedGradientDrawable.setShape(GradientDrawable.OVAL);
                break;
        }
        unSelectedGradientDrawable.setColor(unSelectedIndicatorColor);
        unSelectedGradientDrawable.setSize((int) unSelectedIndicatorWidth, (int) unSelectedIndicatorHeight);
        unSelectedLayerDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
        unSelectedDrawable = unSelectedLayerDrawable;

        selectedGradientDrawable.setColor(selectedIndicatorColor);
        selectedGradientDrawable.setSize((int) selectedIndicatorWidth, (int) selectedIndicatorHeight);
        selectedLayerDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
        selectedDrawable = selectedLayerDrawable;
    }

    /**
     * set list
     *
     * @param list the list of resourceId or urls
     */
    public void setList(List<Object> list) {
        this.list = list;
        itemCount = this.list.size();
        if (itemCount == 0) {
            throw new IllegalStateException("item count not equal zero");
        }
        initSlider();
    }

    /**
     * get index
     *
     * @return pictureIndex index
     */
    public int getPictureIndex() {
        return pictureIndex;
    }

    /**
     * set index
     *
     * @param pictureIndex  index
     */
    public void setPictureIndex(int pictureIndex) {
        this.pictureIndex = pictureIndex;
    }

    public void setListener(IOnClickListener listener) {
        this.listener = listener;
    }

    /**
     * set the dialog
     *
     * @param loadingDialog the loading dialog
     */
    public void setLoadingDialog(Dialog loadingDialog) {
        this.loadingDialog = loadingDialog;
    }

    /**
     * init slider
     */
    private void initSlider() {
        switcherImage = new ImageSwitcher(context);
        switcherImage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        //set factory
        switcherImage.setFactory(this);
        switcherImage.setOnTouchListener(this);
        addView(switcherImage);
        indicatorContainer = new LinearLayout(context);
        indicatorContainer.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        switch (indicatorPosition) {
            case centerBottom:
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case centerTop:
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case leftBottom:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case leftTop:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case rightBottom:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case rightTop:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
        }

        //set margin
        params.setMargins((int) (indicatorMargin), (int) (indicatorMargin), (int) (indicatorMargin), (int) (indicatorMargin));
//        //add indicator into SliderLayout
        addView(indicatorContainer, params);
        //init indicator
        for (int i = 0; i < itemCount; i++) {
            ImageView indicator = new ImageView(context);
            indicator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            indicator.setPadding((int) (indicatorSpace), (int) (indicatorSpace), (int) (indicatorSpace), (int) (indicatorSpace));
            indicator.setImageDrawable(unSelectedDrawable);
            indicatorContainer.addView(indicator);
            final int finalI = i;
            indicator.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAutoPlay();
                    switchIndicator(finalI);
                    pictureIndex = finalI;
                    handler.sendEmptyMessageDelayed(START_AUTO_PLAY, autoPlayDuration);
                }
            });
        }
        switchIndicator(0);
        startAutoPlay();
    }

    /**
     * start play
     */
    private void startAutoPlay() {
        //avoid sliding twice
        stopAutoPlay();
        if (isAutoPlay) {
            handler.sendEmptyMessageDelayed(START_AUTO_PLAY, autoPlayDuration);
        }
    }

    /**
     * stop play
     */
    public void stopAutoPlay() {
        if (isAutoPlay) {
            handler.removeMessages(START_AUTO_PLAY);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ////the code that make the height of ImageSwitcher is wrap_content
//        if (switcherImage != null) {
//            heightMeasureSpec = switcherImage.getMeasuredHeight();
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // get the x of touch down
            touchDownX = event.getX();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // get the x of touch up
            touchUpX = event.getX();
            // left to right
            if (touchUpX - touchDownX > 100) {
                stopAutoPlay();
                SliderLeftToRight();
                handler.sendEmptyMessageDelayed(START_AUTO_PLAY, autoPlayDuration);
                // right to left
            } else if (touchDownX - touchUpX > 100) {
                stopAutoPlay();
                SliderRightToLeft();
                handler.sendEmptyMessageDelayed(START_AUTO_PLAY, autoPlayDuration);
                //onClickListener
            } else if (0 == (Math.abs(touchUpX - touchDownX)) || (Math.abs(touchUpX - touchDownX)) < 50) {
                if (listener != null) {
                    stopAutoPlay();
                    listener.onItemClick(view, pictureIndex);
                    handler.sendEmptyMessageDelayed(START_AUTO_PLAY, autoPlayDuration);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Left to Right
     */
    private void SliderLeftToRight() {
        // get current index
        pictureIndex = pictureIndex == 0 ? itemCount - 1
                : pictureIndex - 1;
        // set Animation
        switcherImage.setInAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.slide_in_left));
        switcherImage.setOutAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.slide_out_right));
        // switch indicator
//        switcherImage.setImageResource(image[pictureIndex]);
        switchIndicator(pictureIndex);
    }

    /**
     * load images
     *
     * @param pictureIndex index
     */
    private void loadImage(int pictureIndex) {
        if (list.get(pictureIndex) instanceof String) {
            loadNetImage(pictureIndex);
        } else if (list.get(pictureIndex) instanceof Integer) {
            loadFileImage(pictureIndex);
        }
    }

    /**
     * load the network images
     *
     * @param pictureIndex index
     */
    private void loadNetImage(int pictureIndex) {
        if (list != null && list.size() != 0) {
            Picasso.with(context)
                    .load((String) list.get(pictureIndex))
                    .placeholder(defaultImage)
                    .error(errorImage)
                    .tag(context)
                    .into(mTarget);
        }
    }

    /**
     * load the resource images
     *
     * @param pictureIndex index
     */
    private void loadFileImage(int pictureIndex) {
        if (list != null && list.size() != 0) {
            switcherImage.setImageResource((Integer) list.get(pictureIndex));
        }
    }

    /**
     * Right to Left
     */
    private void SliderRightToLeft() {
        // get current index
        pictureIndex = pictureIndex == itemCount - 1 ? 0
                : pictureIndex + 1;
        // set Animation
        // Custom the slide_out_left and slide_in_right
        switcherImage.setInAnimation(AnimationUtils.loadAnimation(context,
                R.anim.slide_in_right));
        switcherImage.setOutAnimation(AnimationUtils.loadAnimation(context,
                R.anim.slide_out_left));
//                switcherImage.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
//                switcherImage.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        // switch indicator
//        switcherImage.setImageResource(image[pictureIndex]);
        switchIndicator(pictureIndex);
    }

    /**
     * switch indicator
     *
     * @param index index
     */
    private void switchIndicator(int index) {
        for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
            ((ImageView) indicatorContainer.getChildAt(i)).setImageDrawable(i == index ? selectedDrawable : unSelectedDrawable);
        }
        loadImage(index);
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundColor(0xFFFFFFFF);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT, ImageSwitcher.LayoutParams.MATCH_PARENT));
//                if(list.get(pictureIndex) instanceof String){
//                    Picasso.with(context)
//                .load((String) list.get(pictureIndex))
//                .placeholder(R.mipmap.ic_default)
//                .error(R.mipmap.ic_default)
//                .tag(context)
//                .fit()
//                .into(imageView);
//                }else if(list.get(pictureIndex) instanceof Integer){
//                    imageView.setImageResource((Integer) list.get(pictureIndex));
//                }
        return imageView;
    }

    /**
     * show dialog
     */
    public void showDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * dismiss dialog
     */
    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public interface IOnClickListener {
        void onItemClick(View view, int position);
    }

//    @Override
//    public void dispatchWindowVisibilityChanged( int visibility) {
//        super.dispatchWindowVisibilityChanged(visibility);
//        if (visibility == GONE) {
//            if(context!=null){
//                stopAutoPlay();
//            }
//        } else if (visibility == VISIBLE) {
//            if(context!=null) {
//                //reset index
//                pictureIndex = 0;
//                startAutoPlay();
//            }
//        }
//    }

    /**
     * create path
     *
     * @return Path
     */
    private Path createClipPath() {
        final Path path = new Path();

        switch (arcPosition) {
            case bottom: {
                if (arcShape == ArcShape.inSide) {
                    path.moveTo(0, 0);
                    path.lineTo(0, height);
                    path.quadTo(width / 2, height - 2 * arcHeight, width, height);
                    path.lineTo(width, 0);
                    path.close();
                } else {
                    path.moveTo(0, 0);
                    path.lineTo(0, height - arcHeight);
                    path.quadTo(width / 2, height + arcHeight, width, height - arcHeight);
                    path.lineTo(width, 0);
                    path.close();
                }
                break;
            }
            case top:
                if (arcShape == ArcShape.inSide) {
                    path.moveTo(0, height);
                    path.lineTo(0, 0);
                    path.quadTo(width / 2, 2 * arcHeight, width, 0);
                    path.lineTo(width, height);
                    path.close();
                } else {
                    path.moveTo(0, arcHeight);
                    path.quadTo(width / 2, -arcHeight, width, arcHeight);
                    path.lineTo(width, height);
                    path.lineTo(0, height);
                    path.close();
                }
                break;
            case left:
                if (arcShape == ArcShape.inSide) {
                    path.moveTo(width, 0);
                    path.lineTo(0, 0);
                    path.quadTo(arcHeight * 2, height / 2, 0, height);
                    path.lineTo(width, height);
                    path.close();
                } else {
                    path.moveTo(width, 0);
                    path.lineTo(arcHeight, 0);
                    path.quadTo(-arcHeight, height / 2, arcHeight, height);
                    path.lineTo(width, height);
                    path.close();
                }
                break;
            case right:
                if (arcShape == ArcShape.inSide) {
                    path.moveTo(0, 0);
                    path.lineTo(width, 0);
                    path.quadTo(width - arcHeight * 2, height / 2, width, height);
                    path.lineTo(0, height);
                    path.close();
                } else {
                    path.moveTo(0, 0);
                    path.lineTo(width - arcHeight, 0);
                    path.quadTo(width + arcHeight, height / 2, width - arcHeight, height);
                    path.lineTo(0, height);
                    path.close();
                }
                break;
        }

        return path;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            calculateLayout();
        }
    }

    /**
     *calculate layout
     */
    private void calculateLayout() {
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        if (width > 0 && height > 0) {

            clipPath = createClipPath();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && arcShape != ArcShape.inSide) {
                setOutlineProvider(new ViewOutlineProvider() {
//                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setConvexPath(clipPath);
                    }
                });
            }
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (arcHeight > 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            super.dispatchDraw(canvas);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            canvas.drawPath(clipPath, paint);
            canvas.restoreToCount(saveCount);
            paint.setXfermode(null);
        } else {
            super.dispatchDraw(canvas);
        }
    }
}
