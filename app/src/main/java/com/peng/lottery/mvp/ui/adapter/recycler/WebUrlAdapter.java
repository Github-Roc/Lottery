package com.peng.lottery.mvp.ui.adapter.recycler;

import androidx.annotation.Nullable;

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
        helper.setImageBitmap(R.id.web_url_item_icon, Base64Util.base64ToBitmap(item.getCollectionIcon()));
        helper.setText(R.id.web_url_item_title, item.getCollectionTitle());
        helper.setText(R.id.web_url_item_date, item.getCollectionDate());

        helper.addOnClickListener(R.id.web_url_item_layout);
        helper.addOnClickListener(R.id.web_url_item_set_home);
        helper.addOnClickListener(R.id.web_url_item_delete);
    }
}
