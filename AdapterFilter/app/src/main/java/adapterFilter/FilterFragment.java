package adapterFilter;


import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import reddottips.adapterfilter.R;

/**
 * filter
 * Created by kerui on 2016/11/16.
 */

public class FilterFragment extends Fragment implements View.OnClickListener {
    private ListView filterList;
    public List<String> list;
    private boolean ifShouldPop = true;

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    private CallBack callBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_filter_fragment, null);
        filterList = (ListView) rootView.findViewById(R.id.filter_list);
        rootView.findViewById(R.id.black_bg).setOnClickListener(this);
        initView();
        return rootView;
    }

    private void initView() {

        filterList.setAdapter(new filterAdapter());
        filterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popBackStack(position);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.black_bg) {
            popBackStack(FilterConstant.getInstance().DEFAULT_NULL);
        }

    }


    private class filterAdapter extends BaseAdapter {
        private LayoutInflater mInflater = LayoutInflater.from(getActivity());


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item_filter_list, null);
            TextView filterTv = (TextView) view.findViewById(R.id.filter_tv);
            filterTv.setText(list.get(position));
            return view;
        }
    }

    public void popBackStack(int position) {
        getFragmentManager().popBackStack();
        callBack.getItem(position);
    }

    public interface CallBack {
        void getItem(int position);
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimator(transit, enter, nextAnim);
    }
}
