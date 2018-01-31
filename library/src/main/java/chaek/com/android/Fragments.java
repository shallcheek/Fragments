package chaek.com.android;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.Serializable;
import java.util.List;


/**
 * 简单的fragment 加载工具类
 */
public class Fragments {

    public static Builder with(Context context) {
        return new Builder().with(context);
    }

    public static Builder with(Fragment fragment) {
        return new Builder().with(fragment);
    }

    /**
     * 单纯的获取一个Fragment实例 使用方法
     * <pre>
     * <code>
     *  Fragment testFragment= Fragments.get(TestFragment.class)
     *             .putString("title","title")
     *             .get();
     * </code></pre>
     *
     * @param className
     */
    public static Builder get(Class className) {
        return new Builder().fragment(className);
    }

    public static class Builder {
        static final String FRAGMENT_SINGLE = "_fragment_single";
        private Context context;
        /**
         * fragment class
         **/
        private Class fClass;
        /**
         * 实例化过的fragment
         **/
        private Fragment fragment;
        /**
         * fragmentManager
         * 如果是FragmentActivity 获取supportFragmentManager
         * 如果是Fragment 获取childFragmentManager
         **/
        private FragmentManager fragmentManager;
        private FragmentTransaction transaction;

        /**
         * 传递的参数
         * 包含fragment可能存在的
         **/
        private Bundle bundle;
        /**
         * 自定义tag
         **/
        private String tag;
        /**
         * 设置可点击back按钮返回上一个fragment
         **/
        private boolean addToBackStack = false;
        /**
         * 是否隐藏前面的fragment
         * tag不存在只是hide
         * tag为空的话 直接remove
         **/
        private boolean isHideLast = true;
        /**
         * 是否是单例模式 保证FragmentManager只有一个fragment
         **/
        private boolean single = true;
        private boolean fade;
        /**
         * 过度是否加载动画
         **/
        private boolean isAnim;

        /**
         * 设置fragment tag
         * 如果没有设置参数
         * 默认单例模式会使用fragment的class name值作为tag
         * 不然不会设置fragment tag的值
         *
         * @param tag tag text
         */
        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder addToBackStack() {
            this.addToBackStack = true;
            return this;
        }

        public Builder anim() {
            this.isAnim = true;
            return this;
        }


        /**
         * 是否添加动画
         */
        public Builder fade() {
            fade = true;
            return this;
        }

        /**
         * Activity 获取 Builder
         * 并初始化FragileManage 和Transaction
         *
         * @param context activity
         */
        private Builder with(Context context) {
            if (context instanceof FragmentActivity) {
                fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                this.context = context;
                transaction = fragmentManager.beginTransaction();
            } else {
                throw new IllegalArgumentException("not find FragmentActivity");
            }
            return this;
        }

        /**
         * Fragment 获取 Builder
         *
         * @param fragment fragment
         */
        private Builder with(Fragment fragment) {
            if (fragment != null) {
                fragmentManager = fragment.getChildFragmentManager();
                this.context = fragment.getContext();
                transaction = fragmentManager.beginTransaction();
            } else {
                throw new IllegalArgumentException("fragment not null");
            }
            return this;
        }

        /**
         * fragment的调用方式之一
         * 利用Class newInstance 初始化fragment对象
         *
         * @param fClass fragment class
         */
        public Builder fragment(Class fClass) {
            if (fClass == null) {
                throw new NullPointerException("fragment class not null");
            }
            this.fClass = fClass;
            return this;
        }


        /**
         * 传递fragment实例方式
         *
         * @param fragment fragment 实例
         */
        public Builder fragment(Fragment fragment) {
            if (fragment == null) {
                throw new NullPointerException("fragment not null ");
            }
            this.fragment = fragment;
            this.fragment(fragment.getClass());
            return this;
        }

        /**
         * 是否移除前面显示的fragment
         *
         * @param isRemove true 移除(tag值为空就会移除)或者隐藏(tag有值就做隐藏处理) false不处理
         */
        public Builder removeOld(boolean isRemove) {
            this.isHideLast = isRemove;
            return this;
        }

        /**
         * 获取bundle实例
         *
         * @return checkout bundle
         */
        private Bundle getBundle() {
            if (bundle == null) {
                bundle = new Bundle();
            }
            return bundle;
        }

        /**
         * 传递参数 string
         */
        public Builder putString(String key, String value) {
            getBundle().putString(key, value);
            return this;
        }

        /**
         * 传递参数 bundle
         */
        public Builder bundle(Bundle bundle) {
            getBundle().putAll(bundle);
            return this;
        }

        /**
         * 传递参数 int
         */
        public Builder putInt(String key, Integer value) {
            getBundle().putInt(key, value);
            return this;
        }

        /**
         * 传递参数 Double
         */
        public Builder putDouble(String key, double value) {
            getBundle().putDouble(key, value);
            return this;
        }

        /**
         * 传递参数 float
         */
        public Builder putFloat(String key, float value) {
            getBundle().putFloat(key, value);
            return this;
        }

        /**
         * 传递参数 float
         */
        public Builder putBoolean(String key, boolean value) {
            getBundle().putBoolean(key, value);
            return this;
        }

        /**
         * 传递参数 long
         */
        public Builder putLong(String key, Long value) {
            getBundle().putLong(key, value);
            return this;
        }

        /**
         * 传递参数 Serializable
         */
        public Builder putSerializable(String key, Serializable value) {
            getBundle().putSerializable(key, value);
            return this;
        }

        /**
         * 传递参数 Parcelable
         */
        public Builder put(String key, Parcelable value) {
            getBundle().putParcelable(key, value);
            return this;
        }

        /**
         * 单例
         */
        public Builder single() {
            single = true;
            return this;
        }

        public Builder single(boolean single) {
            this.single = single;
            return this;
        }

        /**
         * 非单例模式
         */
        public Builder multi() {
            single = false;
            return this;
        }

        private Fragment into(int containerViewId, boolean isShow) {
            if (fragment == null && fClass != null) {
                try {
                    if (single && isShow) {
                        fragment = getCacheFragment();
                    }
                    if (fragment == null) {
                        fragment = (Fragment) fClass.newInstance();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (fragment != null && single) {
                Fragment o = getCacheFragment();
                if (o != null) {
                    fragment = o;
                }
            }

            if (fragment == null) {
                throw new NullPointerException("fragment not null ");
            }

            //配置传递数据 如果自带的参数存在
            if (fragment.getArguments() != null) {
                getBundle().putAll(fragment.getArguments());
            }
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            //如果至少单纯的获取Fragment
            if (!isShow) {
                return fragment;
            }

            Fragment c = getCurrentFragment();
            //如果正在显示的fragment与需要显示的fragment一样则不继续执行
            if (c != null && c.equals(fragment)) {
                return fragment;
            }

            //隐藏前面所有的Fragment
            if (isHideLast) {
                changeFragmentState(isShow);
            }
            if (!addToBackStack) {
                transaction = transaction.disallowAddToBackStack();
            }

            if (isAnim) {
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            if (fade) {
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            }

            if (!fragment.isAdded()) {
                String t = tag != null ? tag : fragment.getClass().getName().concat(FRAGMENT_SINGLE);
                transaction.add(containerViewId, fragment, single ? t : null);

                if (addToBackStack) {
                    transaction.addToBackStack(t);
                }
            }
            transaction.show(fragment);
            transaction.commit();
            return fragment;

        }

        public Fragment into(int containerViewId) {
            return into(containerViewId, true);
        }

        /**
         * 获取fragment
         */
        public Fragment get() {
            return into(0, false);
        }

        /**
         * 获取现在显示的Fragment
         *
         * @return current fragment
         */
        private Fragment getCurrentFragment() {
            List<Fragment> fragmentList = fragmentManager.getFragments();
            for (Fragment fragment : fragmentList) {
                if (!fragment.isHidden()) {
                    return fragment;
                }
            }
            return null;
        }

        private void changeFragmentState(boolean isShow) {
            List<Fragment> fragmentList = fragmentManager.getFragments();
            for (Fragment fragment : fragmentList) {
                //单例模式的fragment才hide 否则就直接remove
                if (fragment.getTag() != null && isShow) {
                    if (!fragment.isHidden()) {
                        transaction.hide(fragment);
                    }
                } else {
                    transaction.remove(fragment);
                }
            }
        }

        /**
         * @return 从fragment list 寻找是否有缓存的fragment
         */
        private Fragment getCacheFragment() {
            List<Fragment> fragmentList = fragmentManager.getFragments();
            for (Fragment fragment : fragmentList) {
                if (fragment.getClass().equals(fClass) || fragment.getTag().equals(fClass.getName().concat(FRAGMENT_SINGLE))) {
                    return fragment;
                }
            }
            return null;
        }


    }

}
