package com.rtsoftbd.siddiqui.clientmanagement.helper;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.rtsoftbd.siddiqui.clientmanagement.R;
import com.rtsoftbd.siddiqui.clientmanagement.model.Credit;

import java.util.List;


public class RecyclerViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolder> {

    private Credit credit;
    private final List<ItemModel> data;
    private Context context;
    private int who;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public RecyclerViewRecyclerAdapter(Credit credit, final List<ItemModel> data, int who) {
        this.credit = credit;
        this.data = data;
        this.who = who;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_list_row, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemModel item = data.get(position);
        Log.e("position", String.valueOf(position));
        holder.setIsRecyclable(false);
        holder.textView.setText(item.description);
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, item.colorId1));
        holder.expandableLayout.setInRecyclerView(true);
        holder.setIsRecyclable(true);
        holder.expandableLayout.setBackgroundColor(ContextCompat.getColor(context, item.colorId2));
        holder.expandableLayout.setInterpolator(item.interpolator);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
                if (position!=0){
                    holder.totalBalanceTextView.setVisibility(View.GONE);
                    holder.totalCreditTextView.setVisibility(View.GONE);
                    holder.totalPaidTextView.setVisibility(View.GONE);
                }
                if (position!=1){
                    holder.paidListView.setVisibility(View.GONE);
                }
                if (position!=2){
                    holder.creditListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });

        if (who ==0) {
            holder.totalPaidTextView.setText(context.getResources().getString(R.string.totalPaid) + "\t" + String.valueOf(credit.getTotalPaid()));
            holder.totalCreditTextView.setText(context.getResources().getString(R.string.totalCredit) + "\t" + String.valueOf(credit.getTotalCredit()));
            holder.totalBalanceTextView.setText(context.getResources().getString(R.string.balance) + "\t" + String.valueOf(credit.getTotalPaid() - credit.getTotalCredit()));
        }
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, totalPaidTextView, totalCreditTextView, totalBalanceTextView;
        RelativeLayout buttonLayout;
        ListView paidListView, creditListView;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        ExpandableLinearLayout expandableLayout;

        ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView);
            buttonLayout = (RelativeLayout) v.findViewById(R.id.button);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.summeryExpandableLayout);
            totalPaidTextView = (TextView) v.findViewById(R.id.totalPaidTextView);
            totalCreditTextView = (TextView) v.findViewById(R.id.totalCreditTextView);
            totalBalanceTextView = (TextView) v.findViewById(R.id.totalBalanceTextView);
            paidListView = (ListView) v.findViewById(R.id.paidListView);
            creditListView = (ListView) v.findViewById(R.id.creditListView);

        }
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}