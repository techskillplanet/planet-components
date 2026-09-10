import { useEffect, useState } from 'react';
import * as ReactNative from 'react-native';

/** Subscribe to OS reduce-motion preference (iOS/Android). */
export function useReduceMotion() {
  const [reduce, setReduce] = useState(false);
  useEffect(() => {
    const api = ReactNative.AccessibilityInfo;
    if (!api || typeof api.isReduceMotionEnabled !== 'function') {
      return undefined;
    }
    let mounted = true;
    Promise.resolve(api.isReduceMotionEnabled())
      .then((value) => {
        if (mounted) setReduce(Boolean(value));
      })
      .catch(() => {});
    const sub = api.addEventListener?.('reduceMotionChanged', (value) => {
      if (mounted) setReduce(Boolean(value));
    });
    return () => {
      mounted = false;
      if (sub && typeof sub.remove === 'function') sub.remove();
    };
  }, []);
  return reduce;
}
