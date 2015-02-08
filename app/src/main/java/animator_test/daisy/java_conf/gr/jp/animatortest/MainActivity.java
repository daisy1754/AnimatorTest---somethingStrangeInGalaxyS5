package animator_test.daisy.java_conf.gr.jp.animatortest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ANIMATION_DURATION = 1000;
    private static final String OBJECT_ANIMATOR_SCALE_Y = "ObjectAnimator scaleY";
    private static final String ANIMATOR_SET_SCALE_X = "AnimatorSet scaleX";
    private static final String ANIMATION_FROM_XML_SCALE = "Animation from xml scale";
    private static final String NINEOLD_ANDROID_ALPHA = "Nineold android alpha";
    private ImageView imageView;
    private TextView textView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.animation_status_text);
        spinner = (Spinner) findViewById(R.id.type_spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[] {OBJECT_ANIMATOR_SCALE_Y, ANIMATOR_SET_SCALE_X,
                        ANIMATION_FROM_XML_SCALE, NINEOLD_ANDROID_ALPHA}));
        findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation((String) spinner.getSelectedItem());
            }
        });
    }

    private void startAnimation(String animationType) {
        final long startMillis = System.currentTimeMillis();
        final Animator.AnimatorListener listener = getAnimatorListener(animationType, startMillis);
        switch (animationType) {
            case OBJECT_ANIMATOR_SCALE_Y: {
                ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleY", 0.7f, 1.4f);
                animator.setDuration(ANIMATION_DURATION);
                animator.addListener(listener);
                animator.start();
                return;
            }
            case ANIMATOR_SET_SCALE_X: {
                ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleX", 0.7f, 1.4f);
                animator.setDuration(ANIMATION_DURATION);
                animator.addListener(listener);
                AnimatorSet animatorSet = new AnimatorSet();
                Set<Animator> animators = new HashSet<>();
                animators.add(animator);
                animatorSet.playTogether(animators);
                animatorSet.start();
                return;
            }
            case ANIMATION_FROM_XML_SCALE: {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
                animation.setAnimationListener(getAnimationListener(animationType, startMillis));
                imageView.startAnimation(animation);
                return;
            }
            case NINEOLD_ANDROID_ALPHA: {
                com.nineoldandroids.animation.ObjectAnimator animator
                        = com.nineoldandroids.animation.ObjectAnimator.ofFloat(imageView, "alpha", 0.f, 1.f);
                animator.setDuration(ANIMATION_DURATION);
                animator.addListener(getNinefoldListener(animationType, startMillis));
                animator.start();
                return;
            }
        }

    }

    private Animator.AnimatorListener getAnimatorListener(final String animation, final long startMillis) {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animator) {
                Log.d(TAG, animation + " canceled ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                textView.setText(animation + " end in " + (System.currentTimeMillis() - startMillis) + " ms");
                Log.d(TAG, animation + " end ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.d(TAG, animation + " repeat ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationStart(Animator animator) {
                textView.setText(animation + " start");
                Log.d(TAG, animation + " start ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationPause(Animator animator) {
                Log.d(TAG, animation + " pause ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationResume(Animator animator) {
                Log.d(TAG, animation + " resume ," + (System.currentTimeMillis() - startMillis));
            }
        };
    }

    private Animation.AnimationListener getAnimationListener(final String animationName, final long startMillis) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textView.setText(animationName + " start");
                Log.d(TAG, animationName + " start ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(animationName + " end in "+ (System.currentTimeMillis() - startMillis) + "ms");
                Log.d(TAG, animationName + " end ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, animationName + " repeat ," + (System.currentTimeMillis() - startMillis));
            }
        };
    }

    private com.nineoldandroids.animation.Animator.AnimatorListener getNinefoldListener(final String animation, final long startMillis) {
        return new com.nineoldandroids.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {
                Log.d(TAG, animation + " canceled ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                textView.setText(animation + " start");
                Log.d(TAG, animation + " start ," + (System.currentTimeMillis() - startMillis));
            }

            @Override
            public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                textView.setText(animation + " start");
                Log.d(TAG, animation + " start ," + (System.currentTimeMillis() - startMillis));
            }
        };
    }
}
