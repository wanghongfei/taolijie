var Script = function () {

    $.validator.setDefaults({
        submitHandler: function () {
            alert("submitted!");
        }
    });

    $(document).ready(function () {
        /*后台登陆*/
        $('#form-signin').validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: '请填写用户名'
                },
                password: {
                    required: '请填写密码'
                }
            },
            submitHandler: function () {
                var formData = $("#form-signin").serialize();
                $.ajax({
                    url: '/login/admin',
                    type: 'POST',
                    data: formData,
                    success: function (data) {
                        if (data.result) {
                            location.href = "/manage/index";
                        }else{
                            console.log(data);
                            $('#add_err').html(data.message);
                        }
                    }
                });
            }
        });

        $('#categoryForm').validate({
            rules: {
                type: {
                    required: true
                },
                name: {
                    required: true
                },
                memo: {
                    required: true
                },
                level: {
                    required: true
                },
                themeColor: {
                    required: true
                }
            },
            messages: {
                type: {
                    required: "请输入分类类型"
                },
                name: {
                    required: "请输入名称"
                },
                memo: {
                    required: "请输入描述"
                },
                level: {
                    required: "请输入分类级别"
                },
                themeColor: {
                    required: "请输入分类颜色"
                }
            },
            submitHandler: function () {
                console.log("heheh");
                var formData = $("#categoryForm").serialize();
                $.ajax({
                    url: '/manage/addcategory',
                    type: 'POST',
                    data: formData,
                    success: function (data) {
                        if (data.result) {
                            alert('添加成功')
                        }else{
                            console.log(data);
                        }
                    }
                });
            }
        });

        $('#userForm').validate({

            rules: {
                username: {
                    required: true,
                    minlength: 3,
                    maxlength: 15
                },
                password: {
                    required: true,
                    minlength: 6
                },
                role: {
                    required:true
                }

            }, messages: {
                username: {
                    required: '请输入用户名',
                    minlength: '用户名过短',
                    maxlength: '用户名过长'
                },
                password: {
                    required: '请输入密码',
                    minlength: '密码太短,请重新输入'
                },
                role: {
                    required: '请选择类型'
                }
            },
            submitHandler: function () {
                var formData = $("#userForm").serialize();
                $.ajax({
                    url: '/manage/adduser',
                    type: 'POST',
                    data: formData,
                    success: function (data) {
                        if (data.result) {
                            alert('添加成功')
                        }else{
                            console.log(data);
                        }
                    }
                });
            }
        });

        $('#newsForm').validate({
            rules: {
                title: {
                    required: true,
                    maxlength: 30
                }
            },
            messages: {
                title: {
                    required: '新闻标题不能为空',
                    maxlength: '标题过长'
                }
            },
            submitHandler: function () {
                var title = document.getElementById('title').val;
                var content = document.getElementById('content').val;
                var formData = $("#newsForm").serialize();
                console.log("content:"+content);
                console.log(formData);

                $.ajax({
                    url: '/manage/addnews',
                    type: 'POST',
                    data: formData,
                    success: function (data) {
                        if (data.result) {
                            alert('添加成功');
                            console.log(data);
                        }else{
                            console.log(data);
                        }
                    }
                });
            }
        });

        // validate signup form on keyup and submit
        $("#signupForm").validate({
            rules: {
                firstname: "required",
                lastname: "required",
                username: {
                    required: true,
                    minlength: 2
                },
                password: {
                    required: true,
                    minlength: 5
                },
                confirm_password: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                },
                email: {
                    required: true,
                    email: true
                },
                topic: {
                    required: "#newsletter:checked",
                    minlength: 2
                },
                agree: "required"
            },
            messages: {
                firstname: "Please enter your firstname",
                lastname: "Please enter your lastname",
                username: {
                    required: "Please enter a username",
                    minlength: "Your username must consist of at least 2 characters"
                },
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                confirm_password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long",
                    equalTo: "Please enter the same password as above"
                },
                email: "Please enter a valid email address",
                agree: "Please accept our policy"
            }
        });

        // propose username by combining first- and lastname
        $("#username").focus(function () {
            var firstname = $("#firstname").val();
            var lastname = $("#lastname").val();
            if (firstname && lastname && !this.value) {
                this.value = firstname + "." + lastname;
            }
        });

        //code to hide topic selection, disable for demo
        var newsletter = $("#newsletter");
        // newsletter topics are optional, hide at first
        var inital = newsletter.is(":checked");
        var topics = $("#newsletter_topics")[inital ? "removeClass" : "addClass"]("gray");
        var topicInputs = topics.find("input").attr("disabled", !inital);
        // show when newsletter is checked
        newsletter.click(function () {
            topics[this.checked ? "removeClass" : "addClass"]("gray");
            topicInputs.attr("disabled", !this.checked);
        });
    });


}();