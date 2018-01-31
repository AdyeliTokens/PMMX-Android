package com.pmi.ispmmx.maya.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pmi.ispmmx.maya.Adapters.FeedAdapter;
import com.pmi.ispmmx.maya.Modelos.Entidades.Defectos.Defecto;
import com.pmi.ispmmx.maya.Modelos.Entidades.Feed;
import com.pmi.ispmmx.maya.Modelos.Entidades.Paros.Paro;
import com.pmi.ispmmx.maya.R;

import java.util.List;


public class FeedFragment extends Fragment {

    public List<Feed> feedList;
    public RecyclerView.Adapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private View view;
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    public FeedFragment() {

    }


    public static FeedFragment newInstance() {

        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mListener.getFeed();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_feed, container, false);
        elementosUI();
        iniciarRecycle();
        //inicio();
        return view;
    }

    private void inicio() {

        mListener.getFeed();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void elementosUI() {

        mRecycleView = view.findViewById(R.id.rv_feed);
        swipeRefreshLayout = view.findViewById(R.id.sw_feed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListener.refreshFeed();
            }
        });

    }

    private void llenarFeed() {
        if (mListener != null) {
            mListener.getFeed();
        } else {

        }
    }

    public void iniciarAdapter() {

        mAdapter = new FeedAdapter(feedList, R.layout.cardview_elemento_feed, new FeedAdapter.OnItemClickListener() {


            @Override
            public void OnItemClickFalla(Paro paro) {
                mListener.OnClickFeedFalla(paro);
            }

            @Override
            public void OnItemClickDefecto(Defecto defecto) {
                mListener.OnClickFeedDefecto(defecto);
            }
        });

        mAdapter.notifyDataSetChanged();

    }

    private void iniciarRecycle() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView.setHasFixedSize(false);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setAdapter(mAdapter);

    }

    public void llenarInformacion(List<Feed> feeds) {

        feedList.clear();
        feedList.addAll(feeds);
        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


    public interface OnFragmentInteractionListener {
        void getFeed();

        void refreshFeed();

        void OnClickFeedFalla(Paro paro);

        void OnClickFeedDefecto(Defecto defecto);
    }
}
