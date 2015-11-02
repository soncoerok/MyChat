package com.example.nantes.mines.myChat.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nantes.mines.myChat.R;
import com.example.nantes.mines.myChat.model.Message;
import com.example.nantes.mines.myChat.utils.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the listView
 */
public class MessageAdapter extends BaseAdapter {

    /**
     * Context of the application
     */
    private Context context;
    /**
     * List of messages
     */
    private List<Message> messageList = new ArrayList<Message>();

    /**
     * Constructor.
     *
     * @param context     the context to use
     * @param messageList the list of message
     */
    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int position) {
        return false;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    public int getCount() {
        return messageList.size();
    }

    public Object getItem(int position) {
        return messageList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return false;
    }

    /**
     * Get the view used in the listView
     *
     * @param position    the position in the list
     * @param convertView the convertView
     * @param parent      the parent element
     * @return the final view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // If it's one of my message, use the layout "single_message_layout_me".
        // Otherwise, use the layout "single_message_layout_others".
        if (messageList.get(position).getLogin().equals(Data.getLogin())) {
            rowView = layoutInflater.inflate(R.layout.single_message_layout_me, parent, false);
            createViewHolder(rowView);
        } else {
            rowView = layoutInflater.inflate(R.layout.single_message_layout_others, parent, false);
            createViewHolder(rowView);
        }
        // Set the corresponding login and message
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.loginTextView.setText(messageList.get(position).getLogin() + " : ");
        holder.messageTextView.setText(messageList.get(position).getMessage());
        return rowView;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return messageList.size();
    }

    public boolean isEmpty() {
        return messageList.isEmpty();
    }

    /**
     * Updates the listView using a new list of messages
     *
     * @param messages the new list of messages
     */
    public void refill(List<Message> messages) {
        this.messageList.clear();
        this.messageList.addAll(messages);
        notifyDataSetChanged();
    }

    /**
     * Creates the viewHolder
     *
     * @param rowView the rowView in use
     */
    private void createViewHolder(View rowView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.loginTextView = (TextView) rowView.findViewById(R.id.tv_message_login);
        viewHolder.messageTextView = (TextView) rowView.findViewById(R.id.tv_message_text);
        rowView.setTag(viewHolder);
    }

    /**
     * Static class ViewHolder, used for the viewHolder pattern
     */
    private static class ViewHolder {
        TextView loginTextView, messageTextView;
    }
}