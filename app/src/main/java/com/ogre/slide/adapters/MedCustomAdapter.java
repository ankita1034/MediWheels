package com.ogre.slide.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ogre.slide.R;
import com.ogre.slide.activities.MainActivity;
import com.ogre.slide.models.Medicine;

import java.util.ArrayList;

/**
 * Created by Hungry Ogre on 4/11/2016.
 */
public class MedCustomAdapter extends BaseAdapter implements View.OnClickListener {

    private final String TAG="MedCustomAdapter";
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    Medicine tempValues=null;
    int i=0;

    public MedCustomAdapter(Activity a, ArrayList d, Resources resLocal) {

        activity = a;
        data=d;
        res = resLocal;

        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {


        return position;
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder{

        public TextView medicineNameView;
        public TextView priceView;
//        public Button removebutton;
//        public Button addbutton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view=convertView;
        ViewHolder holder;

        if(convertView==null)
        {

            view=inflater.inflate(R.layout.meditem,null);
            holder=new ViewHolder();
            holder.medicineNameView=(TextView)view.findViewById(R.id.medicineView);
            holder.priceView=(TextView)view.findViewById(R.id.priceView);
//            holder.addbutton=(Button)view.findViewById(R.id.addbutton);
//            holder.removebutton=(Button)view.findViewById(R.id.removebutton);
            view.setTag(holder);
        }
        else
            holder=(ViewHolder)view.getTag();
        if (data.size()<=0)
        {

            holder.medicineNameView.setText("No Data");
        }
        else
        {
            tempValues=(Medicine)data.get(position);
            holder.medicineNameView.setText(tempValues.getName()+" "+tempValues.getPower()+" mg");
            holder.priceView.setText("Rs."+Integer.toString(tempValues.getCost()));
            view.setOnClickListener(new OnItemClickListener(position));
        }
        return view;
    }

    private class  OnItemClickListener implements View.OnClickListener{




        private int mposition;
        @Override
        public void onClick(View v) {


            MainActivity mainActivity=(MainActivity)activity;
            mainActivity.onMedClick(mposition);
        }
        OnItemClickListener(int position)
        {


            mposition=position;
        }
    }


}
