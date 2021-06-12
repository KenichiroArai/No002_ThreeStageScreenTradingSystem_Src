package kmg.core.domain.model.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import kmg.core.domain.model.ReflectionModel;

/**
 * リフレクションモデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class ReflectionModelImpl implements ReflectionModel {

    /** オブジェクト */
    private final Object object;

    /** クラス */
    private final Class<?> clazz;

    /** 最後に取得したフィールド */
    private Field lastGetField;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param object
     *               対象オブジェクト
     */
    public ReflectionModelImpl(final Object object) {
        this.object = object;
        this.clazz = object.getClass();
    }

    /**
     * 最後に取得したフィールドを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 最後に取得したフィールド
     */
    @Override
    public Field getLastGetField() {
        final Field result = this.lastGetField;
        return result;
    }

    /**
     * フィールド名に該当するフィールドに値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param fieldName
     *                  フィールド名
     * @param value
     *                  値
     */
    @Override
    public void set(final String fieldName, final Object value) {

        this.lastGetField = null;
        Class<?> targetClazz = this.clazz;
        try {
            while (targetClazz != Object.class) {
                try {
                    try {
                        this.lastGetField = targetClazz.getField(fieldName);
                    } catch (@SuppressWarnings("unused") final NoSuchFieldException e) {
                        this.lastGetField = targetClazz.getDeclaredField(fieldName);
                    }
                    if (this.lastGetField != null) {
                        this.lastGetField.setAccessible(true);

                        try {
                            Object fieldValue = value;
                            if (value != null) {
                                if (this.lastGetField.getType() == BigDecimal.class) {
                                    try {
                                        fieldValue = new BigDecimal(value.toString());
                                    } catch (@SuppressWarnings("unused") final NumberFormatException e) {
                                        fieldValue = null;
                                    }
                                }
                            }
                            this.lastGetField.set(this.object, fieldValue);
                        } catch (final IllegalAccessException e) {
                            // TODO 2021/06/06 KenichiroArai KMGの例外にする
                            e.printStackTrace();
                        }

                        targetClazz = targetClazz.getSuperclass();
                    }
                } catch (@SuppressWarnings("unused") final NoSuchFieldException e) {
                    targetClazz = targetClazz.getSuperclass();
                }
            }
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }

        if (this.lastGetField == null) {
            return;
        }

        this.lastGetField.setAccessible(true);

        try {
            Object fieldValue = value;
            if (value != null) {
                if (this.lastGetField.getType() == BigDecimal.class) {
                    try {
                        fieldValue = new BigDecimal(value.toString());
                    } catch (@SuppressWarnings("unused") final NumberFormatException e) {
                        fieldValue = null;
                    }
                }
            }
            this.lastGetField.set(this.object, fieldValue);
        } catch (final IllegalAccessException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }

    }

    /**
     * フィールド名に該当するフィールドに値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param fieldName
     *                  フィールド名
     * @return 値
     */
    @Override
    public Object get(final String fieldName) {

        Object result = null;

        this.lastGetField = null;
        Class<?> targetClazz = this.clazz;

        try {
            while (targetClazz != Object.class) {
                try {
                    try {
                        this.lastGetField = targetClazz.getField(fieldName);
                    } catch (@SuppressWarnings("unused") final NoSuchFieldException e) {
                        this.lastGetField = targetClazz.getDeclaredField(fieldName);
                    }
                    if (this.lastGetField != null) {
                        break;
                    }
                } catch (@SuppressWarnings("unused") final NoSuchFieldException e) {
                    targetClazz = targetClazz.getSuperclass();
                }
            }
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }

        if (this.lastGetField == null) {
            return result;
        }

        this.lastGetField.setAccessible(true);

        try {
            result = this.lastGetField.get(this.object);
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }

        return result;

    }

    /**
     * メソッド名に該当するメソッドを呼び出す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param methodName
     *                   メソッド名
     * @param parameters
     *                   パラメータ
     * @return 返却値
     */
    @Override
    public Object getMethodInvoke(final String methodName, final Object... parameters) {

        Object result = null;

        /* パラメータクラスの設定 */
        final Class<?>[] parameterTypes = new Class[parameters.length];
        int i = 0;
        for (final Object param : parameters) {
            parameterTypes[i] = param.getClass();
            i++;
        }

        /* メソッドの取得 */
        Method method = null;
        Class<?> targetClazz = this.clazz;
        try {
            while (targetClazz != Object.class) {
                try {
                    try {
                        method = targetClazz.getMethod(methodName, parameterTypes);
                    } catch (@SuppressWarnings("unused") final NoSuchMethodException e) {
                        method = targetClazz.getDeclaredMethod(methodName, parameterTypes);
                    }
                    if (method != null) {
                        break;
                    }
                } catch (@SuppressWarnings("unused") final NoSuchMethodException e) {
                    targetClazz = targetClazz.getSuperclass();
                }
            }
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }
        if (method == null) {
            return result;
        }

        /* privateメソッドのアクセス許可の設定 */
        method.setAccessible(true);

        /* メソッドの呼び出し */
        try {
            result = method.invoke(this.object, parameters);
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }

        return result;

    }

    /**
     * メソッド名に該当するメソッドを呼び出す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param methodName
     *                       メソッド名
     * @param parameterTypes
     *                       パラメータ型
     * @param parameters
     *                       パラメータ
     * @return 返却値
     */
    @Override
    public Object getMethodInvoke(final String methodName, final Class<?>[] parameterTypes,
        final Object... parameters) {

        Object result = null;

        /* メソッドの取得 */
        Method method = null;
        Class<?> targetClazz = this.clazz;
        try {
            while (targetClazz != Object.class) {
                try {
                    try {
                        method = targetClazz.getMethod(methodName, parameterTypes);
                    } catch (@SuppressWarnings("unused") final NoSuchMethodException e) {
                        method = targetClazz.getDeclaredMethod(methodName, parameterTypes);
                    }
                    if (method != null) {
                        break;
                    }
                } catch (@SuppressWarnings("unused") final NoSuchMethodException e) {
                    targetClazz = targetClazz.getSuperclass();
                }
            }
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }
        if (method == null) {
            return result;
        }

        /* privateメソッドのアクセス許可の設定 */
        method.setAccessible(true);

        /* メソッドの呼び出し */
        try {
            result = method.invoke(this.object, parameters);
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }

        return result;

    }

    /**
     * メソッド名に該当するメソッドを呼び出す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param methodName
     *                   メソッド名
     * @param parameters
     *                   パラメータ
     * @return 返却値
     */
    @Override
    public Object getMethod(final String methodName, final Object... parameters) {

        Object result = null;

        /* パラメータクラスの設定 */
        final Class<?>[] parameterTypes = new Class[parameters.length];
        int i = 0;
        for (final Object param : parameters) {
            parameterTypes[i] = param.getClass();
            i++;
        }

        /* メソッドの取得 */
        Method method = null;
        Class<?> targetClazz = this.clazz;
        try {
            while (targetClazz != Object.class) {
                try {
                    try {
                        method = targetClazz.getMethod(methodName, parameterTypes);
                    } catch (@SuppressWarnings("unused") final NoSuchMethodException e) {
                        method = targetClazz.getDeclaredMethod(methodName, parameterTypes);
                    }
                    if (method != null) {
                        break;
                    }
                } catch (@SuppressWarnings("unused") final NoSuchMethodException e) {
                    targetClazz = targetClazz.getSuperclass();
                }
            }
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }
        if (method == null) {
            return result;
        }

        /* privateメソッドのアクセス許可の設定 */
        method.setAccessible(true);

        /* メソッドの呼び出し */
        try {
            result = method.invoke(this.object, parameters);
        } catch (final SecurityException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            // TODO 2021/06/06 KenichiroArai KMGの例外にする
            e.printStackTrace();
        }

        return result;
    }
}
