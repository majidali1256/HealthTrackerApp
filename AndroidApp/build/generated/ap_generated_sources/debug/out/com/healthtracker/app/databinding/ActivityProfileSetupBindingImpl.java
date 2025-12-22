package com.healthtracker.app.databinding;
import com.healthtracker.app.R;
import com.healthtracker.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityProfileSetupBindingImpl extends ActivityProfileSetupBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.progressIndicator, 1);
        sViewsWithIds.put(R.id.tvStep, 2);
        sViewsWithIds.put(R.id.tvTitle, 3);
        sViewsWithIds.put(R.id.tvSubtitle, 4);
        sViewsWithIds.put(R.id.cardProfilePic, 5);
        sViewsWithIds.put(R.id.ivProfilePic, 6);
        sViewsWithIds.put(R.id.tvAddPhoto, 7);
        sViewsWithIds.put(R.id.tilFullName, 8);
        sViewsWithIds.put(R.id.etFullName, 9);
        sViewsWithIds.put(R.id.tilDateOfBirth, 10);
        sViewsWithIds.put(R.id.etDateOfBirth, 11);
        sViewsWithIds.put(R.id.tvGenderLabel, 12);
        sViewsWithIds.put(R.id.chipGroupGender, 13);
        sViewsWithIds.put(R.id.chipMale, 14);
        sViewsWithIds.put(R.id.chipFemale, 15);
        sViewsWithIds.put(R.id.chipOther, 16);
        sViewsWithIds.put(R.id.tilHeight, 17);
        sViewsWithIds.put(R.id.etHeight, 18);
        sViewsWithIds.put(R.id.tilWeight, 19);
        sViewsWithIds.put(R.id.etWeight, 20);
        sViewsWithIds.put(R.id.btnSkip, 21);
        sViewsWithIds.put(R.id.btnNext, 22);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityProfileSetupBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }
    private ActivityProfileSetupBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.button.MaterialButton) bindings[22]
            , (com.google.android.material.button.MaterialButton) bindings[21]
            , (com.google.android.material.card.MaterialCardView) bindings[5]
            , (com.google.android.material.chip.Chip) bindings[15]
            , (com.google.android.material.chip.ChipGroup) bindings[13]
            , (com.google.android.material.chip.Chip) bindings[14]
            , (com.google.android.material.chip.Chip) bindings[16]
            , (com.google.android.material.textfield.TextInputEditText) bindings[11]
            , (com.google.android.material.textfield.TextInputEditText) bindings[9]
            , (com.google.android.material.textfield.TextInputEditText) bindings[18]
            , (com.google.android.material.textfield.TextInputEditText) bindings[20]
            , (android.widget.ImageView) bindings[6]
            , (com.google.android.material.progressindicator.LinearProgressIndicator) bindings[1]
            , (com.google.android.material.textfield.TextInputLayout) bindings[10]
            , (com.google.android.material.textfield.TextInputLayout) bindings[8]
            , (com.google.android.material.textfield.TextInputLayout) bindings[17]
            , (com.google.android.material.textfield.TextInputLayout) bindings[19]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[3]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}