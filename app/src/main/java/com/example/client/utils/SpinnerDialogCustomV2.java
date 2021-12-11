package com.example.client.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.client.R;

import java.util.ArrayList;
import java.util.List;

public class
SpinnerDialogCustomV2<E> extends SpinnerDialog<E> implements SpinnerDialogAdapterV2.onCheckItemListener {
    private ArrayList<E> items;
    private ArrayList<E> itemsRoot;
    private Activity context;
    private String dTitle;
    private String closeTitle = "Close";
    private OnSpinerItemClick onSpinerItemClick;
    private AlertDialog alertDialog;
    private int pos;
    private int style;
    private String check ="";
    private E lastItem;
    private String uncheckMess;
    //hien thi item Tat ca
    private boolean isShowItemChoseAll;
    private String lableItemAll;
    private boolean isChooseMultiItem;
    private boolean isCheckAllInMutilItem = true;
    private boolean isUnSelectAllInMutilItem = false;
    private ArrayList<E> lstItemSelected = new ArrayList<>();
    private CheckBox cb_chose_all;
    private LinearLayout unSelectAllLinear;
    private onMultiItemSelectedListener onMultiItemSelectedListenner;
    private int totalItemCount;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int pageIndex;
    private int pageSize;
    private onLoadmoreListener mOnLoadmoreListener;
    private onClickResearchListener mOnClickResearchListener;
    private SpinnerDialogAdapterV2<E> spinnerDialogAdapter;

    public SpinnerDialogCustomV2(Activity activity, ArrayList<E> items, String dTitle, String closeTitle) {
        super(activity, items, dTitle);
        context = activity;
        this.items = new ArrayList<>(items);
        this.itemsRoot = new ArrayList<>(items);
        this.dTitle = dTitle;
        this.closeTitle = closeTitle;
    }
    public SpinnerDialogCustomV2(Activity activity, ArrayList<E> items, String dTitle, String closeTitle, String check) {
        super(activity, items, dTitle);
        context = activity;
        this.items = new ArrayList<>(items);
        this.itemsRoot = new ArrayList<>(items);
        this.dTitle = dTitle;
        this.closeTitle = closeTitle;
        this.check = check;
    }
    @Override
    public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }
    public SpinnerDialogCustomV2 setChooseMultiItem(boolean chooseMultiItem, onMultiItemSelectedListener onMultiItemSelectedListener) {
        this.isChooseMultiItem = chooseMultiItem;
        this.onMultiItemSelectedListenner = onMultiItemSelectedListener;
        return  this;
    }
    public SpinnerDialogCustomV2 setCheckAllInMutilItem(boolean isCheckAllInMutilItem){
        this.isCheckAllInMutilItem = isCheckAllInMutilItem;
        return this;
    }
    public SpinnerDialogCustomV2 setUnselectAllInMutilItem(boolean isUnSelectAllInMutilItem){
        this.isUnSelectAllInMutilItem = isUnSelectAllInMutilItem;
        return this;
    }
    public SpinnerDialogCustomV2<E> setLstItemSelected(ArrayList<E> lstItemSelected) {
        this.lstItemSelected = lstItemSelected;
        return this;
    }


    public SpinnerDialogCustomV2<E> setEnableCheckAll(boolean isShowItemChoseAll, @NonNull String lableItemAll){
        this.isShowItemChoseAll = isShowItemChoseAll;
        this.lableItemAll = lableItemAll;
        return  this;
    }

    public SpinnerDialogCustomV2<E> setmOnLoadmoreListener(int pageSize, onLoadmoreListener mOnLoadmoreListener) {
        this.pageSize = pageSize;
        this.mOnLoadmoreListener = mOnLoadmoreListener;
        return this;
    }

    public SpinnerDialogCustomV2<E> updateDataShow(List<E> lstMore) {
        this.items.addAll(lstMore);
        spinnerDialogAdapter.addArrSearch(new ArrayList<E>(lstMore));
        spinnerDialogAdapter.notifyDataSetChanged();
        return this;
    }

    public SpinnerDialogCustomV2<E> onRefreshDataShow(List<E> lstMore) {
        this.items.clear();
        this.items.addAll(lstMore);
        spinnerDialogAdapter.refreshLstSearch(new ArrayList<>(lstMore));
        spinnerDialogAdapter.notifyDataSetChanged();
        return this;
    }

    public SpinnerDialogCustomV2<E> onClickReSearchListener(onClickResearchListener mOnClickResearchListener){
        this.mOnClickResearchListener = mOnClickResearchListener;
        return this;
    }

    @Override
    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.spinner_dialog_layoutv2, (ViewGroup) null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        ListView listView = (ListView) v.findViewById(R.id.list);
        TextView tvTitle = (TextView) v.findViewById(R.id.tvTittle);
        TextView tv_btn_ok = (TextView) v.findViewById(R.id.tv_btn_ok);
        TextView tv_btn_search = (TextView) v.findViewById(R.id.tv_btn_search);
        cb_chose_all = (CheckBox) v.findViewById(R.id.cb_chose_all);
        unSelectAllLinear = (LinearLayout) v.findViewById(R.id.linear_unselect_all);

        if (mOnClickResearchListener != null)
            tv_btn_search.setVisibility(View.VISIBLE);

        tv_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (onMultiItemSelectedListenner != null){
                    onMultiItemSelectedListenner.onResult(lstItemSelected);
                }
            }
        });
        if (isChooseMultiItem){
            v.findViewById(R.id.close).setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            unSelectAllLinear.setVisibility(View.GONE);
        }
        if (!Tools.isNullOrEmpty(dTitle)) {
            tvTitle.setText(dTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        spinnerDialogAdapter = new SpinnerDialogAdapterV2(this.items, context,"reportProgess");
        if (isShowItemChoseAll)
            spinnerDialogAdapter.setEnableCheckAll(true, lableItemAll);
        spinnerDialogAdapter.setMultiSelectItem(isChooseMultiItem);
        listView.setAdapter(spinnerDialogAdapter);
        adb.setView(v);
        if (isChooseMultiItem){
            tv_btn_ok.setVisibility(View.VISIBLE);
            if (isCheckAllInMutilItem) {
                cb_chose_all.setVisibility(View.VISIBLE);
                cb_chose_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!cb_chose_all.isChecked()) {
                            lstItemSelected.clear();
                        } else {
                            lstItemSelected.clear();
                            lstItemSelected.addAll(items);
                        }
                        spinnerDialogAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                cb_chose_all.setVisibility(View.GONE);
            }

            if (isUnSelectAllInMutilItem){
                unSelectAllLinear.setVisibility(View.VISIBLE);
                unSelectAllLinear.setOnClickListener( view -> {
                    lstItemSelected.clear();
                    spinnerDialogAdapter.notifyDataSetChanged();
                });

            } else {
                unSelectAllLinear.setVisibility(View.GONE);
            }
        }
        this.alertDialog = adb.create();
        this.alertDialog.getWindow().getAttributes().windowAnimations = this.style;
        this.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.alertDialog.setCancelable(false);
        if (isChooseMultiItem){
            if (!Tools.isNullOrEmpty(lstItemSelected))
                cb_chose_all.setChecked(lstItemSelected.size() == items.size());
            spinnerDialogAdapter.setArrSelected(lstItemSelected);
            spinnerDialogAdapter.setOnCheckItemListener(this);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (!isChooseMultiItem) {
                    onSingleClickItem(view, pos);
                }else {
                    onMultiChoseItem(spinnerDialogAdapter, pos);
                }
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            public void afterTextChanged(Editable editable) {
                spinnerDialogAdapter.myFilter(searchBox.getText().toString().trim());
            }
        });

        tv_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickResearchListener != null)
                    mOnClickResearchListener.onClick(searchBox.getText().toString());
            }
        });

        if (mOnLoadmoreListener != null) {
            pageIndex = 0;
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    final int lastItem = firstVisibleItem + visibleItemCount;
                    if (items.size() % pageSize == 0
                            && lastItem == totalItemCount
                            && scrollState == SCROLL_STATE_IDLE
                            && Tools.isNullOrEmpty(searchBox.getText().toString())) {
                        pageIndex ++;
                        mOnLoadmoreListener.onLoadMore(pageIndex);
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    SpinnerDialogCustomV2.this.firstVisibleItem = firstVisibleItem;
                    SpinnerDialogCustomV2.this.visibleItemCount = visibleItemCount;
                    SpinnerDialogCustomV2.this.totalItemCount = totalItemCount;
                }
            });
        }
        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        this.alertDialog.show();
    }
    private void onMultiChoseItem(SpinnerDialogAdapterV2<E> spinnerDialogAdapter, int pos) {
        E itemChose = items.get(pos - 1);
        boolean alReadyChoose = lstItemSelected.contains(itemChose);
        if (alReadyChoose)lstItemSelected.remove(itemChose);
        else lstItemSelected.add(itemChose);
        cb_chose_all.setChecked(lstItemSelected.size() == itemsRoot.size());
    }
    private void onSingleClickItem(View view , int pos) {
        E itemChose = null;
        if (!isShowItemChoseAll){
            if (items.get(pos).equals(lastItem)) {
                return;
            }
            itemChose = items.get(pos);
        }else {
            if (pos > 0)
                itemChose = items.get(pos - 1);
            if (itemChose != null ) {
                if (itemChose.equals(lastItem))
                    return;
            }
            lastItem = itemChose;
        }
        TextView tv = (TextView) view.findViewById(R.id.tvpstn);
        if (onSpinerItemClick != null)
            onSpinerItemClick.onClick(tv.getText().toString(), itemChose);
        alertDialog.dismiss();
    }
    public void hideKeyBoard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onCheck(boolean isCheckedAll) {
        cb_chose_all.setChecked(isCheckedAll);
    }
    public interface onMultiItemSelectedListener<E>{
        void onResult(ArrayList<E> lstSelect);
    }
    public interface onLoadmoreListener{
        void onLoadMore(int pageIndex);
    }

    public interface onClickResearchListener{
        void onClick(String keySerach);
    }
}
