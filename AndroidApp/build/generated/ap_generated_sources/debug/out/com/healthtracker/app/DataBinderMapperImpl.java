package com.healthtracker.app;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.healthtracker.app.databinding.ActivityDashboardBindingImpl;
import com.healthtracker.app.databinding.ActivityDocumentVaultBindingImpl;
import com.healthtracker.app.databinding.ActivityFoodLoggerBindingImpl;
import com.healthtracker.app.databinding.ActivityLoginBindingImpl;
import com.healthtracker.app.databinding.ActivityMedicalIdBindingImpl;
import com.healthtracker.app.databinding.ActivityMedicationsBindingImpl;
import com.healthtracker.app.databinding.ActivityProfileBindingImpl;
import com.healthtracker.app.databinding.ActivityProfileSetupBindingImpl;
import com.healthtracker.app.databinding.ActivitySettingsBindingImpl;
import com.healthtracker.app.databinding.ActivitySignupBindingImpl;
import com.healthtracker.app.databinding.ActivitySleepAnalysisBindingImpl;
import com.healthtracker.app.databinding.ActivitySymptomCheckerBindingImpl;
import com.healthtracker.app.databinding.ActivityToolsBindingImpl;
import com.healthtracker.app.databinding.ActivityTrendsBindingImpl;
import com.healthtracker.app.databinding.ItemDocumentBindingImpl;
import com.healthtracker.app.databinding.ItemHealthLogBindingImpl;
import com.healthtracker.app.databinding.ItemMedicationBindingImpl;
import com.healthtracker.app.databinding.ItemSymptomBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYDASHBOARD = 1;

  private static final int LAYOUT_ACTIVITYDOCUMENTVAULT = 2;

  private static final int LAYOUT_ACTIVITYFOODLOGGER = 3;

  private static final int LAYOUT_ACTIVITYLOGIN = 4;

  private static final int LAYOUT_ACTIVITYMEDICALID = 5;

  private static final int LAYOUT_ACTIVITYMEDICATIONS = 6;

  private static final int LAYOUT_ACTIVITYPROFILE = 7;

  private static final int LAYOUT_ACTIVITYPROFILESETUP = 8;

  private static final int LAYOUT_ACTIVITYSETTINGS = 9;

  private static final int LAYOUT_ACTIVITYSIGNUP = 10;

  private static final int LAYOUT_ACTIVITYSLEEPANALYSIS = 11;

  private static final int LAYOUT_ACTIVITYSYMPTOMCHECKER = 12;

  private static final int LAYOUT_ACTIVITYTOOLS = 13;

  private static final int LAYOUT_ACTIVITYTRENDS = 14;

  private static final int LAYOUT_ITEMDOCUMENT = 15;

  private static final int LAYOUT_ITEMHEALTHLOG = 16;

  private static final int LAYOUT_ITEMMEDICATION = 17;

  private static final int LAYOUT_ITEMSYMPTOM = 18;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(18);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_dashboard, LAYOUT_ACTIVITYDASHBOARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_document_vault, LAYOUT_ACTIVITYDOCUMENTVAULT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_food_logger, LAYOUT_ACTIVITYFOODLOGGER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_medical_id, LAYOUT_ACTIVITYMEDICALID);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_medications, LAYOUT_ACTIVITYMEDICATIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_profile, LAYOUT_ACTIVITYPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_profile_setup, LAYOUT_ACTIVITYPROFILESETUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_settings, LAYOUT_ACTIVITYSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_signup, LAYOUT_ACTIVITYSIGNUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_sleep_analysis, LAYOUT_ACTIVITYSLEEPANALYSIS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_symptom_checker, LAYOUT_ACTIVITYSYMPTOMCHECKER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_tools, LAYOUT_ACTIVITYTOOLS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.activity_trends, LAYOUT_ACTIVITYTRENDS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.item_document, LAYOUT_ITEMDOCUMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.item_health_log, LAYOUT_ITEMHEALTHLOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.item_medication, LAYOUT_ITEMMEDICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.healthtracker.app.R.layout.item_symptom, LAYOUT_ITEMSYMPTOM);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYDASHBOARD: {
          if ("layout/activity_dashboard_0".equals(tag)) {
            return new ActivityDashboardBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_dashboard is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYDOCUMENTVAULT: {
          if ("layout/activity_document_vault_0".equals(tag)) {
            return new ActivityDocumentVaultBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_document_vault is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYFOODLOGGER: {
          if ("layout/activity_food_logger_0".equals(tag)) {
            return new ActivityFoodLoggerBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_food_logger is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMEDICALID: {
          if ("layout/activity_medical_id_0".equals(tag)) {
            return new ActivityMedicalIdBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_medical_id is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMEDICATIONS: {
          if ("layout/activity_medications_0".equals(tag)) {
            return new ActivityMedicationsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_medications is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPROFILE: {
          if ("layout/activity_profile_0".equals(tag)) {
            return new ActivityProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPROFILESETUP: {
          if ("layout/activity_profile_setup_0".equals(tag)) {
            return new ActivityProfileSetupBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_profile_setup is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSETTINGS: {
          if ("layout/activity_settings_0".equals(tag)) {
            return new ActivitySettingsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_settings is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSIGNUP: {
          if ("layout/activity_signup_0".equals(tag)) {
            return new ActivitySignupBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_signup is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSLEEPANALYSIS: {
          if ("layout/activity_sleep_analysis_0".equals(tag)) {
            return new ActivitySleepAnalysisBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_sleep_analysis is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSYMPTOMCHECKER: {
          if ("layout/activity_symptom_checker_0".equals(tag)) {
            return new ActivitySymptomCheckerBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_symptom_checker is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTOOLS: {
          if ("layout/activity_tools_0".equals(tag)) {
            return new ActivityToolsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_tools is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTRENDS: {
          if ("layout/activity_trends_0".equals(tag)) {
            return new ActivityTrendsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_trends is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMDOCUMENT: {
          if ("layout/item_document_0".equals(tag)) {
            return new ItemDocumentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_document is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMHEALTHLOG: {
          if ("layout/item_health_log_0".equals(tag)) {
            return new ItemHealthLogBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_health_log is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMMEDICATION: {
          if ("layout/item_medication_0".equals(tag)) {
            return new ItemMedicationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_medication is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMSYMPTOM: {
          if ("layout/item_symptom_0".equals(tag)) {
            return new ItemSymptomBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_symptom is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(18);

    static {
      sKeys.put("layout/activity_dashboard_0", com.healthtracker.app.R.layout.activity_dashboard);
      sKeys.put("layout/activity_document_vault_0", com.healthtracker.app.R.layout.activity_document_vault);
      sKeys.put("layout/activity_food_logger_0", com.healthtracker.app.R.layout.activity_food_logger);
      sKeys.put("layout/activity_login_0", com.healthtracker.app.R.layout.activity_login);
      sKeys.put("layout/activity_medical_id_0", com.healthtracker.app.R.layout.activity_medical_id);
      sKeys.put("layout/activity_medications_0", com.healthtracker.app.R.layout.activity_medications);
      sKeys.put("layout/activity_profile_0", com.healthtracker.app.R.layout.activity_profile);
      sKeys.put("layout/activity_profile_setup_0", com.healthtracker.app.R.layout.activity_profile_setup);
      sKeys.put("layout/activity_settings_0", com.healthtracker.app.R.layout.activity_settings);
      sKeys.put("layout/activity_signup_0", com.healthtracker.app.R.layout.activity_signup);
      sKeys.put("layout/activity_sleep_analysis_0", com.healthtracker.app.R.layout.activity_sleep_analysis);
      sKeys.put("layout/activity_symptom_checker_0", com.healthtracker.app.R.layout.activity_symptom_checker);
      sKeys.put("layout/activity_tools_0", com.healthtracker.app.R.layout.activity_tools);
      sKeys.put("layout/activity_trends_0", com.healthtracker.app.R.layout.activity_trends);
      sKeys.put("layout/item_document_0", com.healthtracker.app.R.layout.item_document);
      sKeys.put("layout/item_health_log_0", com.healthtracker.app.R.layout.item_health_log);
      sKeys.put("layout/item_medication_0", com.healthtracker.app.R.layout.item_medication);
      sKeys.put("layout/item_symptom_0", com.healthtracker.app.R.layout.item_symptom);
    }
  }
}
