package SwingPage;

import javax.swing.JPanel;
import util.Config;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class HaoyoulistJpanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public HaoyoulistJpanel() {
		super();
		setLayout(null);

		gengxin();
		
		util.Config.haoyoulistJpanel = this;
	}

	/**
	 * 更新在线好友，把在线放在前面，
	 */
	// 好友在线更新
	public void haoyouOnline() {
		// 在线列表
		String zaixianliebiao = util.Config.haoyou_online;

		String[] uids = zaixianliebiao.split(",");
		Set<String> keys = Config.list.keySet();
		for (String string : keys) {
			Config.list.get(string).setOnline(false);
		}

		if (!zaixianliebiao.equals("notFound") && !zaixianliebiao.trim().equals("")) {
			for (String uid : uids) {
				System.out.println("uid: " + uid);
				if (!uid.trim().equals("")) {
					FaceJPanel faceJPanel = (FaceJPanel) Config.list.get(uid);
					faceJPanel.setOnline(true);					
				}
			}
		}

		Collection<FaceJPanel> faceJPanels = Config.list.values();
		List<FaceJPanel> list = new ArrayList(faceJPanels);
		Collections.sort(list);

		this.removeAll();
		int i = 0;
		for (FaceJPanel faceJPanel : list) {
			faceJPanel.setBounds(2,i++ * 60,546,60);
			this.add(faceJPanel);
		}
		this.setPreferredSize(new Dimension(0, list.size() * 60));
		this.updateUI();
	}

	

	// 更新列表
	public void gengxin() {
		// 好友列表
		String haoyouliebiao = util.Config.haoyou_json_data;
		JSONArray jsonArray = JSONArray.fromObject(haoyouliebiao);

		if (Config.list.size() == 0) {// 第一次加载列表
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);

				Config.list.put(jsonObject.getString("uid"), new FaceJPanel(jsonObject.getString("img"),
						jsonObject.getString("netname"), jsonObject.getString("info"), jsonObject.getString("uid")));
			}
		} else {// 已经有列表数据

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				String uid = jsonObject.getString("uid");

				FaceJPanel faceJPanel = (FaceJPanel) Config.list.get(uid);
				if (faceJPanel != null) {// 已经存在
					faceJPanel.setnetName(jsonObject.getString("netname"));
					faceJPanel.setInfo(jsonObject.getString("info"));
					faceJPanel.setImage(jsonObject.getString("img"));
				} else {// 不存在
					Config.list.put(jsonObject.getString("uid"),
							new FaceJPanel(jsonObject.getString("img"), jsonObject.getString("netname"),
									jsonObject.getString("info"), jsonObject.getString("uid")));

				}
			}
		}
		haoyouOnline();
	}

}
