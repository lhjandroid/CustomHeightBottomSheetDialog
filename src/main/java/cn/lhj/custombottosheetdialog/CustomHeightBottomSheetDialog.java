package cn.lhj.custombottosheetdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Filedescription.
 * 自定义BottomSheetDialogFragment
 * 可以设置弹出的最大高度
 * 可以设置弹出的最新高度
 * @author lihongjun
 * @date 2018/11/16
 */
public class CustomHeightBottomSheetDialog extends BottomSheetDialogFragment {

    private int mMaxHeight; // 弹窗最大高度
    private int mPeekHeight; // 弹出的最小高度
    private Window mWindow;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                setMaxHeight(); // 显示的时候设置dialog高度
            }
        });
        mWindow = dialog.getWindow();
        init();
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setBottomSheetCallback();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPeekHeight > 0) {
            view.setMinimumHeight(mPeekHeight);
        }
    }

    /**
     * 初始化数据
     */
    private void init() {
        mMaxHeight = getMaxHeight();
        mPeekHeight = getPeekHeight();
    }

    /**
     * 设置最大高度
     * @return
     */
    public int getMaxHeight() {
        return 0;
    }


    /**
     * 获取peek高度
     * @return
     */
    public int getPeekHeight() {
        return 0;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
    }

    /**
     * 获取最大高度 0为默认
     * @param height
     */
    public void setMaxHeight(int height) {
        mMaxHeight = height;
        setMaxHeight();
    }

    public void setPeekHeight(int peekHeight) {
        mPeekHeight = peekHeight;
        setPeekHeight();
    }

    private void setMaxHeight() {
        if (mMaxHeight <= 0) {
            return;
        }
        if (mWindow != null) {
            mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mMaxHeight);
            mWindow.setGravity(Gravity.BOTTOM);
        }
    }

    /**
     * 获取dialog的Behavior 并设置 Peek高度
     * @return
     */
    private BottomSheetBehavior getBottomSheetBehavior() {
        if (mBottomSheetBehavior != null) {
            setPeekHeight();
            return mBottomSheetBehavior;
        }
        View view = mWindow.findViewById(android.support.design.R.id.design_bottom_sheet);
        if (view == null) {
            return null;
        }
        mBottomSheetBehavior = BottomSheetBehavior.from(view);
        setPeekHeight();
        return mBottomSheetBehavior;
    }

    private void setPeekHeight() {
        if (mBottomSheetBehavior != null && mPeekHeight > 0) {
            mBottomSheetBehavior.setPeekHeight(mPeekHeight);
        }
    }

    private void setBottomSheetCallback() {
        if (getBottomSheetBehavior() != null) {
            mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetCallback);
        }
    }

    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
            = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @BottomSheetBehavior.State int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
                BottomSheetBehavior.from(bottomSheet).setState(
                        BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
}