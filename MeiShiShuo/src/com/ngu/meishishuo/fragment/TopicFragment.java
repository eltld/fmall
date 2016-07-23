package com.ngu.meishishuo.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ngu.meishishuo.R;
import com.ngu.meishishuo.activity.AddTopicActivity;
import com.ngu.meishishuo.adapter.TopicAdapter;
import com.ngu.meishishuo.bean.Topic;
import com.ngu.meishishuo.utils.MeiShiDao;
import com.ngu.meishishuo.utils.TimeUtil;
import com.ngu.meishishuo.view.MyListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TopicFragment extends Fragment {
	private MyListView mListView;
	private TopicAdapter myAdapter;
	private List<Topic> topicList;
	private TextView textview;
	private EditText edittext;
	private MeiShiDao dao;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//
		View view=inflater.inflate(R.layout.fragment_topic, container,false);
		initView(view);
		initEvent();
		return view;		
	}
	private void initView(View view){
		dao=new MeiShiDao(getActivity());
		mListView = (MyListView) view.findViewById(R.id.topic_xlistview);
		textview=(TextView) view.findViewById(R.id.topic_textview_talk);
		edittext=(EditText) view.findViewById(R.id.topic_edittext);
		Topic topic;
		topicList=new ArrayList<Topic>();
			topic=new Topic();
			topic.setCommentCount("0");
			topic.setContent("得不到的永远在骚动 被偏爱的都有恃无恐 玫瑰的红 容易受伤的梦 握在手中却流失于指缝");
			topic.setName("美食家");
			topic.setTime("2016-05-01");
			topic.setPraiseCount("26");
			topicList.add(topic);
			
			topic=new Topic();
			topic.setCommentCount("0");
			topic.setContent("时雨及芒种，四野皆插秧。家家麦饭美，处处菱歌长。老我成惰农，永日付竹床。衰发短不栉，爱此一雨凉。庭木集奇声，架藤发幽香。莺衣湿不去，劝我持一觞。即今幸无事，际海皆农桑;野老固不穷，击壤歌虞唐");
			topic.setName("陆游");
			topic.setTime("2016-06-05");
			topic.setPraiseCount("164");
			topicList.add(topic);
			
			topic=new Topic();
			topic.setCommentCount("0");
			topic.setContent("今天提前包粽子吃，耶~~");
			topic.setName("breezf");
			topic.setTime("2016-06-08");
			topic.setPraiseCount("63");
			topicList.add(topic);
			
		topicList.addAll(dao.queryAllTopic());
		myAdapter=new TopicAdapter(topicList, getActivity(),edittext);
		mListView.setAdapter(myAdapter);
	}
	private void initEvent(){
		textview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 
				Intent intent=new Intent(getActivity(),AddTopicActivity.class);
				startActivity(intent);
			}
		});
	}
}
