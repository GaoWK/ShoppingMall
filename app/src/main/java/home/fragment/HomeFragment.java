package home.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.gaowk.shoppingmall.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import home.adapter.HomeFragmentAdapter;
import home.bean.ResultBeanData;
import okhttp3.Call;
import okhttp3.Request;
import utils.Constants;

/**
 * Created by GaoWK on 2019/1/25.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private ResultBeanData.ResultBean resultBean;
    private HomeFragmentAdapter adapter;

    @Override
    public View initView() {
        Log.e(TAG, "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);
        // 设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //联网请求数据
        getDataFromNet();
        Log.e(TAG, "主页数据被初始化了");
    }


    private void initListener() {
        // 置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 回到顶部
                rvHome.scrollToPosition(0);
            }
        });

        // 搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        // 消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getDataFromNet() {
        OkHttpUtils.get().url(Constants.HOME_URL).id(100).build().execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {             // 当联网成功后会回调这里
          //  Log.e(TAG, "联网成功" + response);
            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        //         adapter = new HomeRecycleAdapter(mContext, resultBean);
                        //         rvHome.setAdapter(adapter);

                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                if (position <= 3) {
                                    ib_top.setVisibility(View.GONE);
                                } else {
                                    ib_top.setVisibility(View.VISIBLE);
                                }
                                return 1;
                            }
                        });
                        rvHome.setLayoutManager(manager);

                    }
                    break;
                case 101: //                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
//
private void processData(String json) {
    ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
    resultBean = resultBeanData.getResult();
    if(resultBean != null){
        //有数据
        //设置适配器
        adapter = new HomeFragmentAdapter(mContext,resultBean);
        Log.e(TAG,"banner的第一张："+resultBean.getBanner_info().get(0).getImage());
        rvHome.setAdapter(adapter);
        Log.e(TAG,"adapter设置成功");
        GridLayoutManager manager =  new GridLayoutManager(mContext,1);
        //设置跨度大小监听
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position <= 3){
                    //隐藏
                    ib_top.setVisibility(View.GONE);
                }else{
                    //显示
                    ib_top.setVisibility(View.VISIBLE);
                }
                //只能返回1
                return 1;
            }
        });
        //设置布局管理者
        rvHome.setLayoutManager(manager);

    }else{
        //没有数据
    }
    Log.e(TAG,"解析成功=="+resultBean.getHot_info().get(0).getName());
}



}
