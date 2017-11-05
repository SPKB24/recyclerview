package main

import (
	"log"
	"github.com/gin-gonic/gin"
	"os/exec"
)

type Request struct {
	Text    string `form:"text" json:"text"`
}

func main() {
	route := gin.Default()
	route.GET("/text_to_material", textToMaterial)
	route.Run(":8080")
}

func textToMaterial(c *gin.Context) {
	var req Request
	if c.Bind(&req) == nil {
		log.Println("====== Bind By Query String ======")
		log.Println(req.Text)
	}

	if c.BindJSON(&req) == nil {
		log.Println("====== Bind By JSON ======")
		log.Println(req.Text)
	}
	out, err := exec.Command("./predict", req.Text).Output()
	if err != nil {
		log.Println(err.Error())
	}
	c.String(200, string(out))
}
