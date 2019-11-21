package com.daxko.poc.customViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.daxko.poc.R;
import com.daxko.poc.activity.ChallengeListActivity;
import com.daxko.poc.interfaces.CustomAlertDialogListener;


public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    /**
     * Creates a dialog window that uses the default dialog theme.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     *
     */
    private Context mContext;
    private ConstraintLayout mMainLayout;
    CustomAlertDialogListener mCustomAlertDialogListener;
    private Button mKnowledgeBaseBtn, mContactUsBtn, mMyTicketsBtn, mAnswerBotBtn;
    ImageView mImageView;
    private Bundle mBundle;

    public CustomAlertDialog(Context context, final ChallengeListActivity dialogListener, String message) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = context;
        mCustomAlertDialogListener = dialogListener;

        this.setContentView(R.layout.dialog_custom_alert);

        /*TextView messageText = findViewById(R.id.);
        messageText.setText(message);*/
        mKnowledgeBaseBtn = findViewById(R.id.btn_knowledge_base);
        mContactUsBtn = findViewById(R.id.btn_contact_us);
        mKnowledgeBaseBtn = findViewById(R.id.btn_my_tickets);
        mContactUsBtn = findViewById(R.id.btn_answer_bot);

        findViewById(R.id.btn_knowledge_base).setOnClickListener(this);
        findViewById(R.id.btn_contact_us).setOnClickListener(this);
        findViewById(R.id.btn_my_tickets).setOnClickListener(this);
        findViewById(R.id.btn_answer_bot).setOnClickListener(this);
    }

/*    public void setOkBtnText(String text) {
        mOkBtn.setText(text);
    }

    public void setOkBtnText(int textId) {
        mOkBtn.setText(textId);
    }

    public void setCancelBtnText(String text) {
        mCancelBtn.setText(text);
    }

    public void setCancelBtnText(int textId) {
        mCancelBtn.setText(textId);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_knowledge_base:
                CustomAlertDialog.this.dismiss();
                if(mCustomAlertDialogListener != null) {
                    mCustomAlertDialogListener.knowledgeBaseClickHandler();
                }
                break;
            case R.id.btn_contact_us:
                CustomAlertDialog.this.dismiss();
                if(mCustomAlertDialogListener != null) {
                    mCustomAlertDialogListener.contactUsClickHandler();
                }
                break;

            case R.id.btn_my_tickets:
                CustomAlertDialog.this.dismiss();
                if(mCustomAlertDialogListener != null) {
                    mCustomAlertDialogListener.myTicketsClickHandler();
                }
                break;

            case R.id.btn_answer_bot:
                CustomAlertDialog.this.dismiss();
                if(mCustomAlertDialogListener != null) {
                    mCustomAlertDialogListener.answerBotClickHandler();
                }
                break;
        }
    }
}
