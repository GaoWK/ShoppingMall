package app;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.example.gaowk.shoppingmall.R;

/**
 * Created by GaoWK on 2019/2/2.
 */

public class GoodsInfoActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        setContentView(R.layout.activity_goods_info);
        super.onCreate(savedInstanceState, persistentState);
    }
}
