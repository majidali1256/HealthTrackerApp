package com.healthtracker.app.ui.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.healthtracker.app.data.local.dao.UserDao;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class ProfileSetupActivity_MembersInjector implements MembersInjector<ProfileSetupActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<UserDao> userDaoProvider;

  public ProfileSetupActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<UserDao> userDaoProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.userDaoProvider = userDaoProvider;
  }

  public static MembersInjector<ProfileSetupActivity> create(
      Provider<FirebaseAuth> firebaseAuthProvider, Provider<UserDao> userDaoProvider) {
    return new ProfileSetupActivity_MembersInjector(firebaseAuthProvider, userDaoProvider);
  }

  @Override
  public void injectMembers(ProfileSetupActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
    injectUserDao(instance, userDaoProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.profile.ProfileSetupActivity.firebaseAuth")
  public static void injectFirebaseAuth(ProfileSetupActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.profile.ProfileSetupActivity.userDao")
  public static void injectUserDao(ProfileSetupActivity instance, UserDao userDao) {
    instance.userDao = userDao;
  }
}
