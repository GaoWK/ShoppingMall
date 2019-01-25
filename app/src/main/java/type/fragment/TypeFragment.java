package type.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import base.BaseFragment;

/**
 * Created by GaoWK on 2019/1/25.
 */

public class TypeFragment extends BaseFragment {
    private static final String TAG = TypeFragment.class.getSimpleName();
    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG, "购物车视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "购物车数据被初始化了");
        textView.setText("购物车");
    }
}
