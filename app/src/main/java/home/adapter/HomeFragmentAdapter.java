package home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gaowk.shoppingmall.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.GoodsInfoActivity;
import home.bean.ResultBeanData;
import home.fragment.HomeFragment;
import utils.Constants;

/**
 * Created by GaoWK on 2019/1/26.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {
    /**
     * 广告条幅类型
     */
    public static final int BANNER = 0;

    /**
     * 频道类型
     */
    public static final int CHANNEL = 1;
    /**
     * 活动类型
     */
    public static final int ACT = 2;
    /**
     * 秒杀类型
     */
    public static final int SECKILL = 3;
    /**
     * 推荐类型
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsBean";
    private static final String TAG = HomeFragmentAdapter.class.getSimpleName();
    /**
     * 用来初始化布局
     */
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    /**
     * 数据
     */
    private ResultBeanData.ResultBean resultBean;

    /**
     * 当前类型
     */
    private int currentType = BANNER;

    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null), mContext);
        } else if (viewType == ACT) {
            return new ActViewHolder(mContext, mLayoutInflater.inflate(R.layout.act_item, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, mLayoutInflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, mLayoutInflater.inflate(R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mContext, mLayoutInflater.inflate(R.layout.hot_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }

    }


    //主页recyclerVeiw item种类数目
    @Override
    public int getItemCount() {
        return 6;
    }

    /**
     * BannerVeiwHolder
     */
    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            //设置banner数据
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                String img = banner_info.get(i).getImage();
                Log.e(TAG, "getImage==" + i + "==" + banner_info.get(i).getImage());
                imagesUrl.add(img);
            }
            //设置循环指示点
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置手风琴效果
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setImages(imagesUrl);

            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    //联网请求图片-Glid
                    Glide.with(mContext).load(Constants.Base_URl_IMAGE + path).into(imageView);
                    Log.e(TAG, "联网请求图片：" + imageView.toString());
                }
            });

            banner.start();
            //设置item的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                    startGoodsInfoActivity();
                }
            });
        }
    }

    /**
     * 启动商品详情界面
     */
    private void startGoodsInfoActivity() {
        Intent intent = new Intent(mContext,GoodsInfoActivity.class);
        mContext.startActivity(intent);
    }

    /**
     * ChannelVeiwHolder
     */
    private class ChannelViewHolder extends RecyclerView.ViewHolder {
        public GridView gvChannel;
        public Context mContext;

        public ChannelViewHolder(View itemView, Context mContext) {
            super(itemView);
            gvChannel = itemView.findViewById(R.id.gv_channel);
            this.mContext = mContext;
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            gvChannel.setAdapter(new ChannelAdapter(mContext, channel_info));

            //设置点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(mContext, "position==" + i, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * ActViewHolder
     */
    private class ActViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ViewPager act_viewpager;

        public ActViewHolder(Context mContext, View inflate) {
            super(inflate);
            this.mContext = mContext;
            act_viewpager = inflate.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            act_viewpager.setPageMargin(20);
            act_viewpager.setOffscreenPageLimit(3);//>=3
            // 设置动画
            act_viewpager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
            //1.有数据了
            //2.设置适配器
            //=======================================PagerAdapter
            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view == object;
                }

                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    Glide.with(mContext).load(Constants.Base_URl_IMAGE + act_info.get(position).getIcon_url()).into(imageView);
                    //添加到容器中
                    container.addView(imageView);

                    //设置点击事件
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                        }
                    });


                    return imageView;
                }

//                @Override
//                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                    super.destroyItem(container, position, object);
//                }
            });
        }
    }


    /**
     * SeckillViewHolder
     */
    private class SeckillViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private TextView tv_time_seckill;
        public Context mContext;
        private SeckillRecyclerViewAdapter adapter;
        //相差的时间 服务器提供两个时间 开始时间和结束时间 相减得到倒计时时间
        private long dt = 0;

        @SuppressLint("HandlerLeak")
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt = dt - 1000;
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String time = formatter.format(new Date(dt));
                tv_time_seckill.setText(time);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt <= 0) {
                    //把消息移除
                    handler.removeCallbacksAndMessages(null);
                }

            }
        };

        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            tv_more_seckill = itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill = itemView.findViewById(R.id.rv_seckill);
            tv_time_seckill = itemView.findViewById(R.id.tv_time_seckill);
            this.mContext = mContext;
        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            //设置文本(秒杀时间)和recycVewi的数据
            adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info.getList());
            rv_seckill.setAdapter(adapter);
            //设置布局管理器  参数 上下文、方向、是否倒序
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "秒杀" + position, Toast.LENGTH_SHORT).show();

                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);

//                    GoodsBean goodsBean = new GoodsBean();
//                    goodsBean.setCover_price(listBean.getCover_price());
//                    goodsBean.setFigure(listBean.getFigure());
//                    goodsBean.setName(listBean.getName());
//                    goodsBean.setProduct_id(listBean.getProduct_id());
                    startGoodsInfoActivity();
                }
            });

            //秒杀倒计时 -毫秒
            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    /**
     * RecommendViewHolder
     */
    private class RecommendViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private RecommendGridViewAdapter adapter;

        public RecommendViewHolder(Context mContext, View itemVeiw) {
            super(itemVeiw);
            this.mContext = mContext;
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            //设置适配器
            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gv_recommend.setAdapter(adapter);
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);

//                    GoodsBean goodsBean = new GoodsBean();
//                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
//                    goodsBean.setFigure(recommendInfoBean.getFigure());
//                    goodsBean.setName(recommendInfoBean.getName());
//                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
//                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    /**
     * HotViewHolder
     */
    private class HotViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_hot;
        private GridView gv_hot;
        private Context mContext;

        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_hot = itemView.findViewById(R.id.tv_more_hot);
            gv_hot = itemView.findViewById(R.id.gv_hot);
        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            //设置gridVeiw适配器
            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, hot_info);
            gv_hot.setAdapter(adapter);

            //设置item的监听
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                    //热卖商品信息类
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    //商品信息类
//                    GoodsBean goodsBean = new GoodsBean();
//                    goodsBean.setCover_price(hotInfoBean.getCover_price());
//                    goodsBean.setFigure(hotInfoBean.getFigure());
//                    goodsBean.setName(hotInfoBean.getName());
//                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
//                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }
}
