import java.awt.Image;

import javax.swing.ImageIcon;

public  class Live {

		Image live;

		public Live() {
			loadImage();
		}

		private void loadImage() {
			ImageIcon imgIcon = new ImageIcon("star.png");
			live = imgIcon.getImage();
		}

		public Image getImage() {
			return live;
		}
}
