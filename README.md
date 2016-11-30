# AndroidFilterView
android filterView  use for listView or recyclerview


package adapterFilter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import reddottips.adapterfilter.R;

/**
 * 适配所有筛选控件
 * Created by keRui on 2016/11/16.
 */

public class AdapterFilterView extends RelativeLayout implements View.OnClickListener {
    private TextView filterContent;
    private ImageView rightIcon;
    private View view;
    private boolean appear = false;
    private Context context;
    private Drawable press_blue = getResources().getDrawable(R.mipmap.press_blue);
    private Drawable normal_gray = getResources().getDrawable(R.mipmap.normal_gray);
    private Activity act;
    private int mIdContent;
    private List<String> filterList;
    private List<String> tagList;
    private String tag;
    FilterFragment filterFragment = new FilterFragment();

    public ClickCallBack getClickCallBack() {
        return clickCallBack;
    }

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    private ClickCallBack clickCallBack;


    public AdapterFilterView(Context context) {
        super(context);

    }

    public AdapterFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        initAttrs(context, attrs);
    }

    /**
     * 初始化
     */
    private void initView() {
        view = View.inflate(getContext(), R.layout.view_adapter_filter, this);
        view.setOnClickListener(this);
        filterContent = (TextView) view.findViewById(R.id.filter_content);
        rightIcon = (ImageView) view.findViewById(R.id.right_icon);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AdapterFilterView);
        String defaultFilter = array.getString(R.styleable.AdapterFilterView_filterView_default_filter);
        filterContent.setText(defaultFilter);
    }

    /**
     * 初始化
     */

    public void build(Activity act, int id_content, List<String> list, String tag, List<String> tagList) {
        this.filterList = list;
        this.act = act;
        this.mIdContent = id_content;
        this.tag = tag;
        this.tagList = tagList;
    }

    /**
     * 更新RightIcon
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void syncRightIcon() {
        if (appear) {
            rightIcon.setBackground(press_blue);
            filterContent.setTextColor((Color.parseColor("#4081D6")));
        } else {
            rightIcon.setBackground(normal_gray);
            filterContent.setTextColor((Color.parseColor("#4e4e4e")));
        }
    }

    /**
     * 点击事件
     *
     * @param v 控件
     */
    @Override
    public void onClick(View v) {
        if (v == view) {
            if (!appear) {
                appear = true;
                syncRightIcon();
                show();
            } else {
                appear = false;
                syncRightIcon();
                if (filterFragment != null && filterFragment.isVisible()) {
                    filterFragment.popBackStack(FilterConstant.getInstance().DEFAULT_NULL);
                }

            }
        }
    }

    private void show() {
        if (act != null && mIdContent != 0) {
            clearStack();
            FragmentManager fm = act.getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(mIdContent, filterFragment, tag);
            transaction.addToBackStack(null);//将fragment加入返回栈
            filterFragment.list = filterList;
            filterFragment.setCallBack(new FilterFragment.CallBack() {
                @Override
                public void getItem(int filter) {
                    appear = false;
                    syncRightIcon();
                    clickCallBack.syncFilter(filter, tag);
                }
            });
            transaction.commitAllowingStateLoss();

        } else {
            Toast.makeText(getContext(), "act和id_content不可为空", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 清掉堆栈的filterFragment对象
     */
    private void clearStack() {
        for (int i = 0; i < tagList.size(); i++) {
            if (act.getFragmentManager().findFragmentByTag(tagList.get(i)) != null) {
                FilterFragment f = (FilterFragment) act.getFragmentManager().findFragmentByTag(tagList.get(i));
                f.popBackStack(FilterConstant.getInstance().DEFAULT_NULL);
            }

        }

    }

    /**
     * 回调
     */
    public interface ClickCallBack {
        void syncFilter(int position, String tag);
    }

    /**
     * 筛选 默认值
     *
     * @param filter
     */
    public void setFilter(String filter, int position) {

        if (filter != null) {
            filterContent.setText(filter);
            appear = false;
            syncRightIcon();
        }
    }
}

演示如下

![image](https://github.com/Ryanke/AndroidFilterView/blob/master/AdapterFilter/README.gif)


 


