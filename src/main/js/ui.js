var app;

function onReady() {
    Vue.component('demo-grid', {
        template: '#grid-template',
        props: {
            data: Array,
            columns: Array
        }
    });
    app = new Vue({
        el: '#app',
        data: {
            todos: [
                    { vals: ["a", "b"] },
                    { vals: ["c", "d"] },
                    { vals: ["e", "f"] }
                ]
        }
    })
    app.todos = [];
    for(var i = 0;i<10;i++) {
        var r = [];
        for(var k = 0;k<10;k++) {
            r.push("a");
        }
        app.todos.push({vals: r});
    }
}
