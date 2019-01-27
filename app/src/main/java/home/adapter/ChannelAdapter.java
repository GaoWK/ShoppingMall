package home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gaowk.shoppingmall.R;

import java.util.List;

import butterknife.ButterKnife;
import home.bean.ResultBeanData;
import utils.Constants;

/**
 * Created by GaoWK on 2019/1/27.
 */

class ChannelAdapter extends BaseAdapter {

    private static final String TAG = ChannelAdapter.class.getSimpleName();
    private Context mContext;
    private List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info;

    public ChannelAdapter(Context mContext, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = mContext;
        this.channel_info = channel_info;
    }

    @Override
    public int getCount() {
        return channel_info == null ? 0 : channel_info.size();
    }

    @Override
    public Object getItem(int i) {
        return channel_info.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e(TAG,"ChannelAdapter的getVeiw方法");
        ViewHolder holer;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_channel, null);
            holer = new ViewHolder(view);
            view.setTag(holer);
        } else {
            holer = (ViewHolder) view.getTag();
        }

        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(i);
        //设置文字
        holer.tvChannel.setText(channelInfoBean.getChannel_name());
        //联网请求图片
        Glide.with(mContext).load(Constants.Base_URl_IMAGE + channelInfoBean.getImage()).into(holer.ivChannel);
        Log.e(TAG,"channel联网请求图片");
        return view;
    }

    private class ViewHolder {
        private ImageView ivChannel;
        private TextView tvChannel;

        ViewHolder(View view) {
            ivChannel = view.findViewById(R.id.iv_channel);
            tvChannel = view.findViewById(R.id.tv_channel);
        }
    }
}
