package io.wcm.samples.core.extension

import org.apache.sling.api.adapter.SlingAdaptable

/**
 * Adds a typed adaptTo function to SlingAdaptable
 */
inline fun <reified T> SlingAdaptable.adaptTo(): T? {
  return this.adaptTo(T::class.java)
}
