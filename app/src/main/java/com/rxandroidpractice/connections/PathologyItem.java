package com.rxandroidpractice.connections;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rxandroidpractice.R;


/**
 * Created by Federico Torres on 20/5/2016.
 */
public class PathologyItem implements Item, Parcelable {


    Pathology data;

    @Override
    public Pathology getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = (Pathology) data;
    }


    public View getView(View view   , Context context){
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_pathology, null);
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(data.getName());

        return view;
    }

    class ViewHolder{
        TextView textView;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    public PathologyItem() {
    }

    protected PathologyItem(Parcel in) {
        this.data = in.readParcelable(Pathology.class.getClassLoader());
    }

    public static final Creator<PathologyItem> CREATOR = new Creator<PathologyItem>() {
        @Override
        public PathologyItem createFromParcel(Parcel source) {
            return new PathologyItem(source);
        }

        @Override
        public PathologyItem[] newArray(int size) {
            return new PathologyItem[size];
        }
    };
}
