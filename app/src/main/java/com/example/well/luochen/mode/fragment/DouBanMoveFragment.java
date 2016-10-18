package com.example.well.luochen.mode.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.well.luochen.R;
import com.example.well.luochen.utils.GlideUtils;
import com.example.well.luochen.utils.LogUtils;
import com.example.well.luochen.utils.SpacesItemDecoration;
import com.example.well.luochen.utils.jsoup.JsoupUtil;
import com.example.well.luochen.utils.jsoup.Move;
import com.example.well.luochen.view.AutoSwipeRefreshLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by Well on 2016/10/18.
 */
@EFragment(R.layout.fragment_douban_move)
public class DouBanMoveFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewById
    AutoSwipeRefreshLayout asrl;
    @ViewById
    RecyclerView list_rv;
    private FragmentActivity mActivity;
    private List<Move> mMoveList;
    private DouBanMoveAdapter mAdapter;

    @AfterViews
    void initAfterViews() {
        initOwnView();
        initData();
    }

    private void initOwnView() {
        mActivity = getActivity();
        list_rv.setLayoutManager(new LinearLayoutManager(mActivity));
        list_rv.addItemDecoration(new SpacesItemDecoration(10));
        asrl.setOnRefreshListener(this);
        asrl.setColorSchemeColors(ContextCompat.getColor(mActivity, R.color.colorAccent));
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsoupUtil ju = JsoupUtil.getInstance();
                mMoveList = ju.getDoubanReview(mActivity, 0);
                if (null != mMoveList && mMoveList.size() > 0) {

                    LogUtils.logError("jieguo= " + mMoveList.size() + "  " + mMoveList.toString());
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLoadDataSuccess();
                        }
                    });

                }
            }
        }).start();
    }

    private void onLoadDataSuccess() {
        mAdapter = new DouBanMoveAdapter();
        list_rv.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        asrl.setRefreshing(false);
    }


    public class DouBanMoveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public int getItemViewType(int position) {
            if (position == mMoveList.size() - 1 + 1) {//最后一个条目
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view = View.inflate(mActivity, R.layout.item_douban_move, null);
                return new DouBanMoveFragment.DouBanMoveAdapter.ViewHolder(view);
            } else {
                View footerView = View.inflate(mActivity, R.layout.footview_recycle, null);
                return new DouBanMoveFragment.DouBanMoveAdapter.FooterViewHolder(footerView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position != mMoveList.size() - 1 + 1) {
                DouBanMoveFragment.DouBanMoveAdapter.ViewHolder myHolder = (DouBanMoveFragment.DouBanMoveAdapter.ViewHolder) holder;
                Move move = mMoveList.get(position);
                GlideUtils.displayImageView(mActivity, move.coverUrl, myHolder.mIv_cover, R.drawable.load_failed);
                myHolder.mTv_name.setText(move.name);
                myHolder.mTv_comment.setText(move.comment);
                myHolder.mTv_usede.setText(move.used);

            } else {//加载更多
//                loadMoreMVData();
            }
        }

        @Override
        public int getItemCount() {
            return mMoveList.size() + 1;
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView mIv_cover;//封面
            private TextView mTv_name;//名字
            private TextView mTv_comment;//评论
            private TextView mTv_usede;//是否有用

            public ViewHolder(View itemView) {
                super(itemView);
                mIv_cover = (ImageView) itemView.findViewById(R.id.iv_cover);
                mTv_name = (TextView) itemView.findViewById(R.id.tv_name);
                mTv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
                mTv_usede = (TextView) itemView.findViewById(R.id.tv_used);
            }
        }

        class FooterViewHolder extends RecyclerView.ViewHolder {
            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
