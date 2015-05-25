package be.webfactor.inrdiary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.InrMeasurement;

import java.util.ArrayList;
import java.util.List;

public class InrGraph extends View {

	private static final float MINIMUM = 1.0f;
	private static final int LABEL_COUNT = 5;

	private Paint paint = new Paint();
	private List<Float> values = new ArrayList<>();
	private String[] verlabels = new String[0];

	public InrGraph(Context context) {
		super(context);
	}

	public InrGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InrGraph(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (values.isEmpty()) {
			return;
		}

		float border = getResources().getDimension(R.dimen.l);
		float horstart = border * 1.5f;
		float height = getHeight();
		float width = getWidth() - horstart - 1;
		float max = getMax();
		float min = MINIMUM;
		float diff = max - min;
		float graphheight = height - (2 * border);
		float textSize = getResources().getDimension(R.dimen.xs);

		paint.setTextAlign(Paint.Align.LEFT);
		for (int i = 0; i < verlabels.length; i++) {
			float y = textSize + ((height - 1.1f * textSize) / (verlabels.length - 1)) * i;
			paint.setColor(getResources().getColor(R.color.white));
			paint.setTextSize(textSize);
			canvas.drawText(verlabels[i], 0, y, paint);
		}

		if (max != min) {
			paint.setColor(getResources().getColor(R.color.white_transparent));
			float datalength = values.size();
			float colwidth = width / datalength;
			for (int i = 0; i < values.size(); i++) {
				float val = values.get(i) - min;
				float rat = val / diff;
				float h = height * rat;

				float left = (i * colwidth) + horstart;
				float top = (2 * border) - h + graphheight;
				float right = left + (colwidth - 1);
				float bottom = height + 1;
				canvas.drawRect(left, top, right, bottom, paint);
			}
		}
	}

	private float getMax() {
		float largest = Integer.MIN_VALUE;
		for (float value : values)
			if (value > largest)
				largest = value;
		return largest;
	}

	public void setValues(List<Float> values) {
		this.values = values;

		List<String> labels = new ArrayList<>();
		float value = getMax();
		for (int i = 0; i < LABEL_COUNT; i++) {
			labels.add(InrMeasurement.INR_VALUE_NUMBER_FORMAT.format(value));
			value = value - (getMax() - MINIMUM) / (LABEL_COUNT - 1);
		}
		this.verlabels = labels.toArray(new String[labels.size()]);
	}

}