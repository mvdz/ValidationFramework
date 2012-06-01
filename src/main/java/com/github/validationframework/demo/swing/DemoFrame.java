package com.github.validationframework.demo.swing;

import com.github.validationframework.feedback.FeedBack;
import com.github.validationframework.feedback.TriggerFeedBack;
import com.github.validationframework.feedback.swing.AbstractColorFeedBack;
import com.github.validationframework.feedback.swing.AbstractIconFeedBack;
import com.github.validationframework.feedback.swing.AbstractIconTipFeedBack;
import com.github.validationframework.feedback.swing.AbstractToolTipFeedBack;
import com.github.validationframework.rule.Rule;
import com.github.validationframework.trigger.swing.value.TextComponentStringTrigger;
import com.github.validationframework.validator.DefaultValidator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;
import net.miginfocom.swing.MigLayout;

public class DemoFrame extends JFrame {

	/**
	 * UIResource in case look-and-feel changes while result is visible. The new look-and-feel is allowed to replace it
	 * colors, otherwise, we may introduce permanent inconsistencies.
	 */
	private static final Color COLOR_NOK_EMPTY = new ColorUIResource(new Color(226, 125, 125, 127));
	private static final Color COLOR_NOK_TOO_LONG = new ColorUIResource(new Color(226, 125, 125));

	private enum TextFieldResult /*implements AggregatableResult<Boolean>*/ {

		OK(true, "", null, null, null),
		NOK_EMPTY(false, "Should not be empty", "/icons/warning.png", null, COLOR_NOK_EMPTY),
		NOK_TOO_LONG(false, "Cannot be more than 4 characters", "/icons/invalid2.png", COLOR_NOK_TOO_LONG, null);

		private final boolean aggregatableResult;
		private final String text;
		private Icon icon;
		private final Color foreground;
		private final Color background;

		TextFieldResult(final boolean aggregatableResult, final String text, final String iconName,
						final Color foreground, final Color background) {
			this.aggregatableResult = aggregatableResult;
			this.text = text;
			this.foreground = foreground;
			this.background = background;

			// Error icon
			if ((iconName != null) && !iconName.isEmpty()) {
				final InputStream inputStream = getClass().getResourceAsStream(iconName);
				try {
					final BufferedImage image = ImageIO.read(inputStream);
					icon = new ImageIcon(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public Icon getIcon() {
			return icon;
		}

		public Color getForeground() {
			return foreground;
		}

		public Color getBackground() {
			return background;
		}

		@Override
		public String toString() {
			return text;
		}

//		@Override
//		public Boolean getAggregatableResult() {
//			return aggregatableResult;
//		}
	}

	private class TextFieldRule implements Rule<String, TextFieldResult> {

		@Override
		public TextFieldResult validate(final String input) {
			TextFieldResult result = TextFieldResult.OK;

			if ((input == null) || (input.isEmpty())) {
				result = TextFieldResult.NOK_EMPTY;
			} else if (input.length() >= 5) {
				result = TextFieldResult.NOK_TOO_LONG;
			}

			return result;
		}
	}

	private class TextFieldToolTipFeedBack extends AbstractToolTipFeedBack<TextFieldResult> {

		public TextFieldToolTipFeedBack(final JComponent owner) {
			super(owner);
		}

		@Override
		public void feedback(final TextFieldResult result) {
			setToolTipText(result.toString());
			switch (result) {
				case OK:
					hideToolTip();
					break;
				default:
					showToolTip();
			}
		}
	}

	private class TextFieldColorFeedBack extends AbstractColorFeedBack<TextFieldResult> {

		public TextFieldColorFeedBack(final JComponent owner) {
			super(owner);
		}

		@Override
		public void feedback(final TextFieldResult result) {
			setForeground(result.getForeground());
			setBackground(result.getBackground());
			switch (result) {
				case OK:
					hideColors();
					break;
				default:
					showColors();
			}
		}
	}

	private class TextFieldIconFeedBack extends AbstractIconFeedBack<TextFieldResult> {

		public TextFieldIconFeedBack(final JComponent owner) {
			super(owner);
		}

		@Override
		public void feedback(final TextFieldResult result) {
			setIcon(result.getIcon());
			switch (result) {
				case OK:
					hideIconTip();
					break;
				default:
					showIconTip();
			}
		}
	}

	private class TextFieldIconTipFeedBack extends AbstractIconTipFeedBack<TextFieldResult> {

		public TextFieldIconTipFeedBack(final JComponent owner) {
			super(owner);
		}

		@Override
		public void feedback(final TextFieldResult result) {
			setIcon(result.getIcon());
			setToolTipText(result.toString());
			switch (result) {
				case OK:
					hideIconTip();
					break;
				default:
					showIconTip();
			}
		}
	}

	private enum GroupResult {
		OK(true),
		NOK(false);

		private final boolean applyEnabled;

		GroupResult(final boolean applyEnabled) {
			this.applyEnabled = applyEnabled;
		}

		public boolean isApplyEnabled() {
			return applyEnabled;
		}
	}

	private class GroupRule implements Rule<TextFieldResult, GroupResult> {

		@Override
		public GroupResult validate(final TextFieldResult input) {
			return GroupResult.OK;
		}
	}

	private static final long serialVersionUID = -2039502440268195814L;

	public DemoFrame() {
		super();
		init();
	}

	private void init() {
		setTitle("Validation Framework Test");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Create contents
		final JPanel contentPane = new JPanel(
				new MigLayout("fill, wrap 2", "[]related[grow]", "[]related[]related[]related[]unrelated[]"));
		setContentPane(contentPane);

		// First textfield
		contentPane.add(new JLabel("Tooltip:"));
		JTextField textField = new JTextField();
		contentPane.add(textField, "growx");
		final DefaultValidator<String, TextFieldResult> validator1 = new DefaultValidator<String, TextFieldResult>();
		validator1.addTrigger(new TextComponentStringTrigger(textField));
		validator1.addRule(new TextFieldRule());
		validator1.addFeedBack(new TextFieldToolTipFeedBack(textField));
		final TriggerFeedBack<TextFieldResult> groupTrigger1 = new TriggerFeedBack<TextFieldResult>();
		validator1.addFeedBack(groupTrigger1);

		// Second textfield
		contentPane.add(new JLabel("Color:"));
		textField = new JTextField();
		contentPane.add(textField, "growx");
		final DefaultValidator<String, TextFieldResult> validator2 = new DefaultValidator<String, TextFieldResult>();
		validator2.addTrigger(new TextComponentStringTrigger(textField));
		validator2.addRule(new TextFieldRule());
		validator2.addFeedBack(new TextFieldColorFeedBack(textField));
		final TriggerFeedBack<TextFieldResult> groupTrigger2 = new TriggerFeedBack<TextFieldResult>();
		validator2.addFeedBack(groupTrigger2);

		// Third textfield
		contentPane.add(new JLabel("Icon:"));
		textField = new JTextField();
		contentPane.add(textField, "growx");
		final DefaultValidator<String, TextFieldResult> validator3 = new DefaultValidator<String, TextFieldResult>();
		validator3.addTrigger(new TextComponentStringTrigger(textField));
		validator3.addRule(new TextFieldRule());
		validator3.addFeedBack(new TextFieldIconFeedBack(textField));
		final TriggerFeedBack<TextFieldResult> groupTrigger3 = new TriggerFeedBack<TextFieldResult>();
		validator3.addFeedBack(groupTrigger3);

		// Fourth textfield
		contentPane.add(new JLabel("Icon tip:"));
		textField = new JTextField();
		contentPane.add(textField, "growx");
		final DefaultValidator<String, TextFieldResult> validator4 = new DefaultValidator<String, TextFieldResult>();
		validator4.addTrigger(new TextComponentStringTrigger(textField));
		validator4.addRule(new TextFieldRule());
		validator4.addFeedBack(new TextFieldIconTipFeedBack(textField));
		final TriggerFeedBack<TextFieldResult> groupTrigger4 = new TriggerFeedBack<TextFieldResult>();
		validator4.addFeedBack(groupTrigger4);

		// Apply button
		final JButton applyButton = new JButton("Apply");
		contentPane.add(applyButton, "growx, span");
		final DefaultValidator<TextFieldResult, GroupResult> groupValidator =
				new DefaultValidator<TextFieldResult, GroupResult>();
		groupValidator.addTrigger(groupTrigger1);
		groupValidator.addTrigger(groupTrigger2);
		groupValidator.addTrigger(groupTrigger3);
		groupValidator.addTrigger(groupTrigger4);
		groupValidator.addRule(new Rule<TextFieldResult, GroupResult>() {
			@Override
			public GroupResult validate(TextFieldResult input) {
				// Need source!!
				// TODO
				return null;
			}
		});
		groupValidator.addFeedBack(new FeedBack<GroupResult>() {
			@Override
			public void feedback(GroupResult result) {
				applyButton.setEnabled(GroupResult.OK.equals(result));
			}
		});

		// Set size
		pack();
		final Dimension size = getSize();
		size.width += 100;
		setMinimumSize(size);

		// Set location
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 3);
	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				// Set look-and-feel
				try {
					for (final UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (UnsupportedLookAndFeelException e) {
					// handle exception
				} catch (ClassNotFoundException e) {
					// handle exception
				} catch (InstantiationException e) {
					// handle exception
				} catch (IllegalAccessException e) {
					// handle exception
				}

				// Show window
				new DemoFrame().setVisible(true);
			}
		});
	}
}
