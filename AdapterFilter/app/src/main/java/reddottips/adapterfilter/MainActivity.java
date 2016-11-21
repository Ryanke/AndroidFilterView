package reddottips.adapterfilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import adapterFilter.AdapterFilterView;
import adapterFilter.FilterConstant;

public class MainActivity extends AppCompatActivity implements AdapterFilterView.ClickCallBack {
    private AdapterFilterView mChannelFilter;
    private AdapterFilterView mTypeFilter;
    private AdapterFilterView mStateFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFilter();
    }


    private void initFilter() {
        mTypeFilter = (AdapterFilterView) findViewById(R.id.type_filter);
        mStateFilter = (AdapterFilterView) findViewById(R.id.state_filter);
        mChannelFilter = (AdapterFilterView) findViewById(R.id.channel_filter);
        mChannelFilter.build(this, R.id.activity_main, FilterConstant.getInstance().channelFilter, "mChannelFilter", FilterConstant.getInstance().tagList);
        mStateFilter.build(this, R.id.activity_main, FilterConstant.getInstance().stateFilter, "mStateFilter", FilterConstant.getInstance().tagList);
        mTypeFilter.build(this, R.id.activity_main, FilterConstant.getInstance().typeFilter, "mTypeFilter", FilterConstant.getInstance().tagList);
        mTypeFilter.setClickCallBack(this);
        mStateFilter.setClickCallBack(this);
        mChannelFilter.setClickCallBack(this);

    }


    @Override
    public void syncFilter(int position, String tag) {
        /**
         *当点击区域为非列表时不做ui更新
         * 当筛选条件为全部时显示默认筛选值
         */

        if (position == FilterConstant.getInstance().DEFAULT_NULL) {
            return;
        }
        switch (tag) {
            case "mChannelFilter":
                if (position == 0) {
                    mChannelFilter.setFilter("渠道", position);
                } else {
                    mChannelFilter.setFilter(FilterConstant.getInstance().channelFilter.get(position), position);
                }
                break;
            case "mStateFilter":

                if (position == 0) {
                    mStateFilter.setFilter("状态", position);
                } else {
                    mStateFilter.setFilter(FilterConstant.getInstance().stateFilter.get(position), position);
                }
                break;
            case "mTypeFilter":
                if (position == 0) {
                    mTypeFilter.setFilter("签约方式", position);
                } else {
                    mTypeFilter.setFilter(FilterConstant.getInstance().typeFilter.get(position), position);
                }
                break;
            default:
                break;

        }

    }
}
