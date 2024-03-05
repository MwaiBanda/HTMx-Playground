package com.mwaibanda.htmx_playground

import data.HttpFactory
import data.PostRepositoryImpl
import domain.repository.PostRepository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h5
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.p
import kotlinx.html.role
import kotlinx.html.script
import kotlinx.html.style

fun main() {
    embeddedServer(
        Netty,
        port = 8081,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    val postRepository: PostRepository = PostRepositoryImpl(HttpFactory.createClient())
    routing {
        get("/") {
            call.respondHtml {
                head {
                    link {
                        href = "https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.css"
                        rel = "stylesheet"
                    }
                }
                body {
                    attributes["x-data"] = "{ showClearPosts: true }"
                    div {
                        classes += "flex justify-between max-w-full"
                        div("flex") {

                            button {
                                classes += "text-xl font-bold bg-white text-white border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 px-5 py-2 rounded-md m-3"
                                attributes["x-cloak"] = "true"
                                attributes["x-show"] = "showClearPosts"
                                attributes["hx-get"] = "/posts"
                                attributes["hx-trigger"] = "load"
                                attributes["hx-target"] = "#content"
                                attributes["x-on:click"] = "document.getElementById('content').innerHTML = \"\"; showClearPosts = false;"
                                +"""Clear"""
                            }
                            button {
                                classes += "x-cloak text-xl font-bold bg-white text-white border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 px-5 py-2 rounded-md m-3"
                                attributes["x-cloak"] = "true"
                                attributes["x-show"] = "!showClearPosts"
                                attributes["hx-get"] = "/posts"
                                attributes["hx-target"] = "#content"
                                attributes["x-on:click"] = "showClearPosts = true"
                                +"""Get Random Posts"""
                            }
                        }


                        div("flex items-center"){
                            attributes["x-data"] = "{ count: 0 }"

                            button {
                                classes += "text-xl font-bold bg-white text-white border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 px-5 py-2 rounded-md m-3"
                                attributes["x-cloak"] = "true"
                                attributes["x-on:click"] = "count--"
                                +"""-"""
                            }
                            p("text-xl font-bold"){
                                attributes["x-cloak"] = "true"
                                attributes["x-text"] = "count"
                                +"0"
                            }
                            button {
                                classes += "text-xl font-bold bg-white text-white border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 px-5 py-2 rounded-md m-3"
                                attributes["x-cloak"] = "true"
                                attributes["x-on:click"] = "count++"
                                +"""+"""
                            }
                        }
                    }

                    div {
                        classes += "m-3"
                        id = "content"
                    }
                    script { src = "https://unpkg.com/alpinejs" }
                    script { src = "https://cdn.tailwindcss.com?plugins=forms,typography,aspect-ratio,line-clamp" }
                    script { src = "https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js" }
                    script {
                        src = "https://unpkg.com/htmx.org@1.9.10"
                        defer = true
                    }

                }
            }
        }

        get("/posts") {
            val posts = postRepository.getPosts()
            call.respondHtml {
                body {
                    posts.map {
                        a(classes = "block max-w-full p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 my-3") {
                            href = "#"
                            h5("mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white") { +it.title }
                            p("font-normal text-gray-700 dark:text-gray-400") { +it.body }
                        }
                    }
                }
            }
        }
    }
}