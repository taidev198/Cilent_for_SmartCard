package com.example.client.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.client.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SpinnerDialogAdapterV2<E> extends BaseAdapter {
    private Context mContext;
    private ArrayList<E> arrPstn;
    private ArrayList<E> arrSearch = new ArrayList<E>();
    private ArrayList<E> arrSelected = new ArrayList<E>();
    private String check = "";
    //hien thi item Tat ca
    private boolean isShowItemChoseAll;
    private String labelItemAll;
    private boolean isMultiSelectItem;
    private onCheckItemListener onCheckItemListener;

    public SpinnerDialogAdapterV2(ArrayList<E> arr, Context context) {
        this.arrPstn = arr;
        mContext = context;
        arrSearch.addAll(arrPstn);
    }

    public SpinnerDialogAdapterV2(ArrayList<E> arr, Context context, String check) {
        this.arrPstn = arr;
        mContext = context;
        arrSearch.addAll(arrPstn);
        this.check = check;
    }

    public SpinnerDialogAdapterV2 refreshLstSearch(ArrayList<E> arr) {
        this.arrSearch.clear();
        this.arrSearch.addAll(arr);
        return this;
    }

    public SpinnerDialogAdapterV2 addArrSearch(ArrayList<E> arr) {
        this.arrSearch.addAll(arr);
        return this;
    }

    public SpinnerDialogAdapterV2 setEnableCheckAll(boolean isShowItemChoseAll, @NonNull String labelItemAll) {
        this.isShowItemChoseAll = isShowItemChoseAll;
        this.labelItemAll = labelItemAll;
        return this;
    }

    public void setArrSelected(ArrayList<E> arrSelected) {
        this.arrSelected = arrSelected;
        notifyDataSetChanged();
    }

    public void setMultiSelectItem(boolean multiSelectItem) {
        isMultiSelectItem = multiSelectItem;
    }

    @Override
    public int getCount() {
        if (isShowItemChoseAll)
            return arrPstn.size() + 1;
        else
            return arrPstn.size();
    }

    public void setOnCheckItemListener(onCheckItemListener onCheckItemListener) {
        this.onCheckItemListener = onCheckItemListener;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    class ViewHolder {
        TextView txtpstn;
        CheckBox cbSelect;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        final ViewHolder holder;
        View mView = arg1;

        if (mView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            mView = inflater.inflate(R.layout.item_spinner_ctbh, arg2,
                    false);
            holder = new ViewHolder();
            holder.txtpstn = mView.findViewById(R.id.tvpstn);
            holder.cbSelect = mView.findViewById(R.id.cb_select);
            if (isMultiSelectItem)
                holder.cbSelect.setVisibility(View.VISIBLE);
            mView.setTag(holder);
        } else {
            holder = (ViewHolder) mView.getTag();
        }
        if (isShowItemChoseAll && arg0 == 0) {
            holder.txtpstn.setText(labelItemAll);
            return mView;
        }
        int position = arg0;
        if (isShowItemChoseAll)
            position -= 1;
        String pstn = arrPstn.get(position).toString();
        holder.txtpstn.setText(pstn);
        if (!"reportProgess".equals(check)) {
            holder.txtpstn.setMaxLines(1);
            holder.txtpstn.setSingleLine(true);
        }
        if (isMultiSelectItem && !Tools.isNullOrEmpty(arrPstn)) {
            holder.cbSelect.setChecked(arrSelected.contains(arrPstn.get(position)));
            final int finalPosition = position;
            holder.cbSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.cbSelect.isChecked()) {
                        arrSelected.add(arrPstn.get(finalPosition));
                    } else {
                        arrSelected.remove(arrPstn.get(finalPosition));
                    }
                    if (onCheckItemListener != null) {
                        onCheckItemListener.onCheck(arrSelected.size() == arrPstn.size());
                    }
                }
            });
        }
        return mView;
    }


    public List<E> myFilter(String searchKey) {
        searchKey = Tools.convertUnicode2Nosign(searchKey.toLowerCase(Locale.getDefault()));
        arrPstn.clear();
        if (searchKey.length() == 0) {
            arrPstn.addAll(arrSearch);
        } else {
            for (E item : arrSearch) {
                if (Tools.isNullOrEmpty(item) || Tools.isNullOrEmpty(item.toString())) {
                    continue;
                }
                String productname = Tools.convertUnicode2Nosign(item.toString().toLowerCase(Locale.getDefault()));
                if (productname.contains(searchKey)) {
                    arrPstn.add(item);
                }
            }
        }
        notifyDataSetChanged();
        return arrPstn;
    }

    //bo dau ky tu co dau
    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public interface onCheckItemListener<E> {
        void onCheck(boolean isCheckedAll);
    }
}
