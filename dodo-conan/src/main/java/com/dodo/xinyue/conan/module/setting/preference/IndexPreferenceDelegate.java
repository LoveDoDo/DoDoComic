package com.dodo.xinyue.conan.module.setting.preference;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.constant.ApiConstants;
import com.dodo.xinyue.conan.helper.ApiHelper;
import com.dodo.xinyue.conan.main.index.dialog.ConanListDialog;
import com.dodo.xinyue.core.delegates.DoDoDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页偏好设置
 *
 * @author DoDo
 * @date 2018/10/30
 */
public class IndexPreferenceDelegate extends DoDoDelegate {

    private int mLanguageIndex = 0;
    private int mTypeIndex = 0;
    private int mSourceIndex = 0;
    private int mFormIndex = 0;

    private List<Integer> mLanguageIndexList = null;
    private List<Integer> mTypeIndexList = null;
    private List<Integer> mSourceIndexList = null;
    private List<Integer> mFormIndexList = null;

    @BindView(R2.id.tvLanguage)
    TextView mTvLanguage = null;
    @BindView(R2.id.tvType)
    TextView mTvType = null;
    @BindView(R2.id.tvSource)
    TextView mTvSource = null;
    @BindView(R2.id.tvForm)
    TextView mTvForm = null;

    @OnClick(R2.id.tvBack)
    void onTvBackClicked() {
        pop();
    }

    @OnClick(R2.id.rlLanguage)
    void onLanguageClicked() {
        final int size = mLanguageIndexList.size();
        final String[] languageStrArray = new String[size];
        for (int i = 0; i < size; i++) {
            languageStrArray[i] = ApiHelper.getLanguageStr(mLanguageIndexList.get(i));
        }
        ConanListDialog.builder()
                .title("切换语言")
                .addItems(languageStrArray, mLanguageIndexList.indexOf(mLanguageIndex))
                .onSelected(selectedIndex -> {
                    mLanguageIndex = mLanguageIndexList.get(selectedIndex);
                    mTvLanguage.setText(ApiHelper.getLanguageStr(mLanguageIndex));
                    ApiHelper.saveDefaultIndex(ApiConstants.KEY_LANGUAGE_DEFAULT_INDEX, mLanguageIndex);
                })
                .options(ApiConstants.LIST_DIALOG_OPTIONS)
                .build()
                .show();
    }

    @OnClick(R2.id.rlType)
    void onTypeClicked() {
        final int size = mTypeIndexList.size();
        final String[] typeStrArray = new String[size];
        for (int i = 0; i < size; i++) {
            typeStrArray[i] = ApiHelper.getTypeStr(mTypeIndexList.get(i));
        }
        ConanListDialog.builder()
                .title("切换版本")
                .addItems(typeStrArray, mTypeIndexList.indexOf(mTypeIndex))
                .onSelected(selectedIndex -> {
                    mTypeIndex = mTypeIndexList.get(selectedIndex);
                    mTvType.setText(ApiHelper.getTypeStr(mTypeIndex));
                    ApiHelper.saveDefaultIndex(ApiConstants.KEY_TYPE_DEFAULT_INDEX, mTypeIndex);
                })
                .options(ApiConstants.LIST_DIALOG_OPTIONS)
                .build()
                .show();
    }

    @OnClick(R2.id.rlSource)
    void onSourceClicked() {
        final int size = mSourceIndexList.size();
        final String[] sourceStrArray = new String[size];
        for (int i = 0; i < size; i++) {
            sourceStrArray[i] = ApiHelper.getSourceStr(mSourceIndexList.get(i));
        }
        ConanListDialog.builder()
                .title("切换平台")
                .addItems(sourceStrArray, mSourceIndexList.indexOf(mSourceIndex))
                .onSelected(selectedIndex -> {
                    mSourceIndex = mSourceIndexList.get(selectedIndex);
                    mTvSource.setText(ApiHelper.getSourceStr(mSourceIndex));
                    ApiHelper.saveDefaultIndex(ApiConstants.KEY_SOURCE_DEFAULT_INDEX, mSourceIndex);
                })
                .options(ApiConstants.LIST_DIALOG_OPTIONS)
                .build()
                .show();
    }

    @OnClick(R2.id.rlForm)
    void onFormClicked() {
        final int size = mFormIndexList.size();
        final String[] formStrArray = new String[size];
        for (int i = 0; i < size; i++) {
            formStrArray[i] = ApiHelper.getFormStr(mFormIndexList.get(i));
        }
        ConanListDialog.builder()
                .title("切换列表")
                .addItems(formStrArray, mFormIndexList.indexOf(mFormIndex))
                .onSelected(selectedIndex -> {
                    mFormIndex = mFormIndexList.get(selectedIndex);
                    mTvForm.setText(ApiHelper.getFormStr(mFormIndex));
                    ApiHelper.saveDefaultIndex(ApiConstants.KEY_FORM_DEFAULT_INDEX, mFormIndex);
                })
                .options(ApiConstants.LIST_DIALOG_OPTIONS)
                .build()
                .show();
    }

    public static IndexPreferenceDelegate create() {
        return new IndexPreferenceDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index_preference;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mLanguageIndexList = ApiHelper.getIndexPreferenceConfig(ApiConstants.KEY_LANGUAGE);
        mTypeIndexList = ApiHelper.getIndexPreferenceConfig(ApiConstants.KEY_TYPE);
        mSourceIndexList = ApiHelper.getIndexPreferenceConfig(ApiConstants.KEY_SOURCE);
        mFormIndexList = ApiHelper.getIndexPreferenceConfig(ApiConstants.KEY_FORM);

        mLanguageIndex = ApiHelper.getDefaultIndex(ApiConstants.KEY_LANGUAGE_DEFAULT_INDEX);
        mTypeIndex = ApiHelper.getDefaultIndex(ApiConstants.KEY_TYPE_DEFAULT_INDEX);
        mSourceIndex = ApiHelper.getDefaultIndex(ApiConstants.KEY_SOURCE_DEFAULT_INDEX);
        mFormIndex = ApiHelper.getDefaultIndex(ApiConstants.KEY_FORM_DEFAULT_INDEX);

        mTvLanguage.setText(ApiHelper.getLanguageStr(mLanguageIndex));
        mTvType.setText(ApiHelper.getTypeStr(mTypeIndex));
        mTvSource.setText(ApiHelper.getSourceStr(mSourceIndex));
        mTvForm.setText(ApiHelper.getFormStr(mFormIndex));

    }

}
