package lesson2.tool

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

object WebDevTool {

    private val viewCountFile = File("views.txt")
    private val count = AtomicInteger(0)

    fun createHelloWorldPage() {
        File("index.html").writeText(
            """
            <!DOCTYPE html>
            <html>
            <head><title>Hello</title></head>
            <body><h1>Hello World</h1></body>
            </html>
        """.trimIndent()
        )
    }

    fun createDraggableBoxPage() {
        File("index.html").writeText(
            """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { margin:0; height:100vh; background:#f0f0f0; overflow:hidden; }
                    #box { width:100px; height:100px; background:deepskyblue; position:absolute; cursor:grab; border-radius:12px; }
                </style>
            </head>
            <body>
                <div id="box"></div>
                <script>
                    const box = document.getElementById('box');
                    box.onmousedown = function(e) {
                        let shiftX = e.clientX - box.getBoundingClientRect().left;
                        let shiftY = e.clientY - box.getBoundingClientRect().top;
                        function moveAt(pageX, pageY) {
                            box.style.left = pageX - shiftX + 'px';
                            box.style.top = pageY - shiftY + 'px';
                        }
                        function onMouseMove(e) { moveAt(e.pageX, e.pageY); }
                        document.addEventListener('mousemove', onMouseMove);
                        box.onmouseup = function() {
                            document.removeEventListener('mousemove', onMouseMove);
                            box.onmouseup = null;
                        };
                    };
                </script>
            </body>
            </html>
        """.trimIndent()
        )
    }

    fun createPageViewPage() {
        if (!viewCountFile.exists()) viewCountFile.writeText("0")
        File("index.html").writeText(
            """
            <!DOCTYPE html>
            <html>
            <head><title>Page Views</title></head>
            <body><h1>Total Page Views: <span id="count"></span></h1>
            <script>
                fetch('/count').then(res => res.text()).then(c => {
                    document.getElementById('count').innerText = c;
                })
            </script></body>
            </html>
        """.trimIndent()
        )
    }

    fun createTodoPage() {
        File("index.html").writeText(
            """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Todo List</title>
                <style>
                    body { background:#e0e0e0; font-family:sans-serif; display:flex; flex-direction:column; align-items:center; padding-top:50px; }
                    .todo-box { background:#e0e0e0; box-shadow:9px 9px 16px #bebebe, -9px -9px 16px #ffffff;
                                border-radius:20px; width:300px; padding:20px; }
                    input { width:80%; padding:10px; border:none; border-radius:10px; box-shadow:inset 2px 2px 5px #bebebe, inset -2px -2px 5px #ffffff; }
                    button { margin-left:10px; padding:10px 15px; border:none; border-radius:10px; background:#e0e0e0;
                             box-shadow:3px 3px 6px #bebebe, -3px -3px 6px #ffffff; cursor:pointer; }
                    li { list-style:none; margin:10px 0; padding:10px; background:#e0e0e0; border-radius:10px;
                         box-shadow:inset 2px 2px 5px #bebebe, inset -2px -2px 5px #ffffff; display:flex; justify-content:space-between; align-items:center; }
                </style>
            </head>
            <body>
                <div class="todo-box">
                    <h2>📝 Neumorphism Todo</h2>
                    <div><input id="todoInput" placeholder="Enter todo"/><button onclick="addTodo()">Add</button></div>
                    <ul id="todoList"></ul>
                </div>
                <script>
                    let todos = JSON.parse(localStorage.getItem('todos') || '[]');
                    const list = document.getElementById('todoList');
                    function render() {
                        list.innerHTML = '';
                        todos.forEach((t, i) => {
                            const li = document.createElement('li');
                            li.innerHTML = t + '<button onclick="del('+i+')">🗑️</button>';
                            list.appendChild(li);
                        });
                        localStorage.setItem('todos', JSON.stringify(todos));
                    }
                    function addTodo() {
                        const input = document.getElementById('todoInput');
                        if (input.value.trim()) { todos.push(input.value.trim()); input.value=''; render(); }
                    }
                    function del(i){ todos.splice(i,1); render(); }
                    render();
                </script>
            </body>
            </html>
        """.trimIndent()
        )
    }

    fun serveWebPage(request: String) {
        if ("page views" in request.lowercase()) {
            count.set(viewCountFile.readText().toInt())
        }

        embeddedServer(Netty, port = 8080) {
            routing {
                get("/") {
                    call.respondText(File("index.html").readText(), ContentType.Text.Html)
                }
                if ("page views" in request.lowercase()) {
                    get("/count") {
                        val updated = count.incrementAndGet()
                        viewCountFile.writeText(updated.toString())
                        call.respondText(updated.toString())
                    }
                }
            }
        }.start(wait = true)
    }
}
