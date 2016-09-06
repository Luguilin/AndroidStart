package lgl.androidstart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @param <T>
 * @author LGL
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData;
    protected LayoutInflater mInflater;
    int layoutId;

    public CommonAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, position, layoutId, convertView, parent);
        convert(viewHolder, mData.get(position));
        return viewHolder.getConvertView();
    }

    ;

    public List<T> getData() {
        return mData;
    }

    public abstract void convert(ViewHolder holder, T t);
}
