package com.example.administrator.day1028.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.day1028.R;
import com.example.administrator.day1028.adapters.NetEaseAdapter;
import com.example.administrator.day1028.base.LazyBaseFragment;
import com.example.administrator.day1028.biz.Xhttp;
import com.example.administrator.day1028.commom.CommonUrls;
import com.example.administrator.day1028.entity.NetEase;
import com.example.administrator.day1028.view.RecycleViewDivider;

import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends LazyBaseFragment implements SwipeRefreshLayout.OnRefreshListener, NetEaseAdapter.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_TID = "key_tid";
    private static final String KEY_TNAME = "key_tname";
    @BindView(R.id.recyclerView1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.swipe1)
    SwipeRefreshLayout mSwipe1;

    private String tid, tname;


    private OnFragmentInteractionListener mListener;
    private RecyclerView.OnScrollListener lis = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!mSwipe1.isRefreshing()) {
                int lastItemPosition = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == mNetEaseAdapter.getItemCount() - 1) {
                    //加载数据
                    mNetEaseAdapter.setCurrentState(NetEaseAdapter.FOOTER_PULLING);

                    //获取新数据，url
                    Xhttp.getNewsList(CommonUrls.getUrl(tid), tid, new Xhttp.OnSuccessListener() {
                        @Override
                        public void setNewsList(List<NetEase> neteaseNews) {
                            mNetEaseAdapter.addDataList(neteaseNews);
                            mNetEaseAdapter.notifyDataSetChanged();
                            if (neteaseNews.size() == 0) {
                                mNetEaseAdapter.setCurrentState(NetEaseAdapter.FOOTER_PULL_NO_DATA);
                            } else {
                                mNetEaseAdapter.setCurrentState(NetEaseAdapter.FOOTER_PULL_FINISHED);
                            }
                        }
                    });
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
    private NetEaseAdapter mNetEaseAdapter;
    private LinearLayoutManager layoutManager;
    private Xhttp.OnSuccessListener listener = new Xhttp.OnSuccessListener() {
        @Override
        public void setNewsList(List<NetEase> neteaseNews) {
            mNetEaseAdapter = new NetEaseAdapter(neteaseNews);
            mNetEaseAdapter.setOnItemClickListener(NewsListFragment.this);
            mRecyclerView1.setAdapter(mNetEaseAdapter);
            //必须要设置一个布局管理器 //listview,gridview,瀑布流
            layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView1.setLayoutManager(layoutManager);
            //   mRecyclerView1.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
            // mRecyclerView1.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.HORIZONTAL, false));
            //   mRecyclerView1.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            //分割线
            mRecyclerView1.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        }
    };
    private Handler mHandler;

    public NewsListFragment() {
        // Required empty public constructor
    }

    @Override
    protected boolean lazyLoad() {
        Log.d(TAG, "lazyLoad: 加载数据");
        //        mTvText.setText(tid + "------" + tname);
        mSwipe1.setOnRefreshListener(this);
        mRecyclerView1.addOnScrollListener(lis);
        Xhttp.getNewsList(CommonUrls.getUrl(tid), tid, listener);
        // Xhttp.getNewsList(url, "T1370583240249", listener);
        return true;

    }

    @Override
    protected void initData() {

        mSwipe1.setOnRefreshListener(this);
        mRecyclerView1.addOnScrollListener(lis);
        Xhttp.getNewsList(CommonUrls.getUrl(tid), tid, listener);
        // Xhttp.getNewsList(url, "T1370583240249", listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }


    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance(String tid, String tname) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TID, tid);
        args.putString(KEY_TNAME, tname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tid = getArguments().getString(KEY_TID);
            tname = getArguments().getString(KEY_TNAME);
        }
        mHandler = new Handler();
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        Runnable runable = new TimerTask() {
            @Override
            public void run() {
                NetEase netEase = mNetEaseAdapter.getDataList().get(1);
                mNetEaseAdapter.addData(1, netEase);
                mNetEaseAdapter.notifyItemInserted(1);
                Toast.makeText(getContext(), "加载数据完毕", Toast.LENGTH_SHORT).show();
                mSwipe1.setRefreshing(false);

            }
        };
        mHandler.postDelayed(runable, 2000);
    }

    private NetEaseAdapter.OnItemClickListener onlistener;

    @Override
    public void onClick(int position) {
        onButtonPressed(mNetEaseAdapter.getDataList().get(position).docid);

    }

    public void onButtonPressed(String docId) {
        if (mListener != null) {
            mListener.onFragmentInteraction(docId);
        }
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(String docId);
    }
}
