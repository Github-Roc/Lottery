package com.peng.lottery.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.lottery.R;
import com.peng.lottery.app.utils.Base64Util;
import com.peng.lottery.mvp.model.db.bean.WebUrl;

import java.util.List;

public class WebUrlAdapter extends BaseQuickAdapter<WebUrl, BaseViewHolder> {

    public WebUrlAdapter(int layoutResId, @Nullable List<WebUrl> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WebUrl item) {
        helper.setImageBitmap(R.id.tv_url_icon, Base64Util.base64ToBitmap(item.getCollectionIcon()));
        helper.setText(R.id.tv_url_title, item.getCollectionTitle());
        helper.setText(R.id.tv_collection_date, item.getCollectionDate());

        helper.addOnClickListener(R.id.layout_content);
        helper.addOnClickListener(R.id.bt_web_set_home);
        helper.addOnClickListener(R.id.bt_web_url_delete);
    }
}
